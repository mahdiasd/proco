package com.proco.data.remote.utils

import com.proco.domain.model.network.DataResult
import com.proco.domain.model.network.NetworkError
import com.proco.domain.model.network.NetworkException
import com.proco.domain.model.network.NetworkResponse
import com.proco.extention.eLog
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection


suspend fun <T : Any> safeCall(execute: suspend () -> NetworkResponse<T>?): DataResult<T> {
    return try {
        val response = execute()
        if (response != null && response.status == "success" && response.data != null) {
            DataResult.Success(response.data!!)
        } else {
            "${response!!.getParseMessage().toString()} ".eLog(tag = "Retrofit", plusTag = "safeCall is not successful")
            DataResult.Failure(NetworkError.Unknown)
        }
    } catch (e: Throwable) {
        "${e.message} ".eLog(tag = "Retrofit", plusTag = "safeCall exception")
        DataResult.Failure(getError(e))
    }
}

fun getError(throwable: Throwable): NetworkError {
    return try {
        when (throwable) {
            is IOException -> NetworkError.IoException
            is IllegalArgumentException -> NetworkError.IllegalArgumentException
            is HttpException -> {
                val errorBody: String = throwable.response()?.errorBody()?.string() ?: ""
                val message = Json.decodeFromString<NetworkException?>(errorBody)?.getParseMessage().toString() ?: ""

                when (throwable.code()) {
                    // unauthorized
                    HttpURLConnection.HTTP_UNAUTHORIZED -> NetworkError.HttpException(message)

                    // not found
                    HttpURLConnection.HTTP_NOT_FOUND -> NetworkError.NotFound

                    // access denied
                    HttpURLConnection.HTTP_FORBIDDEN -> NetworkError.AccessDenied

                    // unavailable service
                    HttpURLConnection.HTTP_SERVER_ERROR -> NetworkError.ServerUnavailable

                    // all the others will be treated as unknown error
                    else -> NetworkError.Unknown
                }
            }

            else -> NetworkError.Unknown
        }
    } catch (e: Exception) {
        "${e.message} ".eLog(tag = "Retrofit", plusTag = "safeCall getError exception")
        NetworkError.Unknown
    }
}

