package com.proco.data

import com.proco.data.remote.utils.getError
import com.proco.data.remote.utils.safeCall
import com.proco.domain.model.network.DataResult
import com.proco.domain.model.network.NetworkError
import com.proco.domain.model.network.NetworkResponse
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class SafeCallTest {

    @Test
    fun `safeCall handles failed response`() = runBlocking {
        val result = safeCall {
            NetworkResponse(status = null, appException = null, message = null, data = null)
        }

        assertTrue(result is DataResult.Failure)
        assertEquals(NetworkError.Unknown, (result as DataResult.Failure).networkError)
    }

    @Test
    fun `safeCall handles exceptions`() = runBlocking {
        val result = safeCall<IOException> {
            throw IOException("Network error")
        }

        assertTrue(result is DataResult.Failure)
        assertEquals(NetworkError.IoException, (result as DataResult.Failure).networkError)
    }

    @Test
    fun `getError handles IOException`() {
        val error = getError(IOException())
        assertEquals(NetworkError.IoException, error)
    }

    @Test
    fun `getError handles IllegalArgumentException`() {
        val error = getError(IllegalArgumentException())
        assertEquals(NetworkError.IllegalArgumentException, error)
    }

    @Test
    fun `getError handles HttpException 401`() {
        val error = getError(
            HttpException(
                Response.error<String>(401, "".toResponseBody("application/json".toMediaType()))
            )
        )
        assertEquals(NetworkError.Unauthorized, error)
    }

    @Test
    fun `getError parses error body for HttpException 400`() {
        val body = """{"message":["email is already exist","country_id must be a number conforming to the specified constraints"],"error":"Bad Request","statusCode":400}"""
        val error = getError(
            HttpException(
                Response.error<String>(400, body.toResponseBody("application/json".toMediaType()))
            )
        )
        val expected = NetworkError.HttpException("email is already exist\ncountry_id must be a number conforming to the specified constraints")
        assertEquals(expected, error)
    }

    @Test
    fun `getError handles unknown HttpException`() {
        val exception = HttpException(Response.error<String>(500 , "".toResponseBody("application/json".toMediaType())))
        val error = getError(exception)
        assertEquals(NetworkError.ServerUnavailable, error)
    }
}