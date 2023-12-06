package com.proco.data.model

import com.proco.domain.model.network.ErrorEntity
import com.proco.domain.model.network.NetworkResponse
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class ErrorHandlerImpl : ErrorHandler {

    override fun getError(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is IOException -> ErrorEntity.IoException
            is HttpException -> {
                val errorBody: String = throwable.response()?.errorBody()?.string() ?: ""
                val message = Json.decodeFromString<NetworkResponse?>(errorBody)?.getMessage() ?: ""

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
                    else -> ErrorEntity.Unknown(message)
                }
            }
            else -> ErrorEntity.Unknown(throwable.message.toString())
        }
    }
}