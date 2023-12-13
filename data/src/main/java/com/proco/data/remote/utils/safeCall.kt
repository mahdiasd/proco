package com.proco.data.remote.utils

import com.proco.domain.model.network.DataResult
import com.proco.domain.model.network.ErrorEntity
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
            DataResult.Failure(ErrorEntity.Unknown)
        }
    } catch (e: Throwable) {
        "${e.message} ".eLog(tag = "Retrofit", plusTag = "safeCall exception")
        DataResult.Failure(getError(e))
    }
}

fun getError(throwable: Throwable): ErrorEntity {
    return try {
        when (throwable) {
            is IOException -> ErrorEntity.IoException
            is IllegalArgumentException -> ErrorEntity.IllegalArgumentException
            is HttpException -> {
                val errorBody: String = throwable.response()?.errorBody()?.string() ?: ""
                val message = Json.decodeFromString<NetworkResponse<*>?>(errorBody)?.getParseMessage().toString() ?: ""

                when (throwable.code()) {
                    // unauthorized
                    HttpURLConnection.HTTP_UNAUTHORIZED -> ErrorEntity.HttpException(message)

                    // not found
                    HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound

                    // access denied
                    HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.AccessDenied

                    // unavailable service
                    HttpURLConnection.HTTP_SERVER_ERROR -> ErrorEntity.ServerUnavailable

                    // all the others will be treated as unknown error
                    else -> ErrorEntity.Unknown
                }
            }

            else -> ErrorEntity.Unknown
        }
    } catch (e: Exception) {
        "${e.message} ".eLog(tag = "Retrofit", plusTag = "safeCall exception")
        ErrorEntity.Unknown
    }
}

