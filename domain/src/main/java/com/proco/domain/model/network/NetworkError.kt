package com.proco.domain.model.network

sealed class NetworkError {
    data object IoException : NetworkError()
    data object NotFound : NetworkError()
    data object AccessDenied : NetworkError()
    data object Unauthorized : NetworkError()
    data object Unknown : NetworkError()
    data object IllegalArgumentException : NetworkError()
    data object ServerUnavailable : NetworkError()
    data class HttpException(val message: String) : NetworkError()
}

fun NetworkError.getUiMessage(): String {
    return when (this) {
        is NetworkError.AccessDenied -> "Access denied, please re-login"
        is NetworkError.HttpException -> return message
        is NetworkError.IoException -> "Connection error. Please check network."
        is NetworkError.NotFound -> "Not found. Check details and try again."
        is NetworkError.ServerUnavailable -> "Server unavailable. Try again later."
        is NetworkError.Unknown -> return "Unexpected error occurred. Try again later."
        is NetworkError.IllegalArgumentException -> "Invalid input. Please check and try again."
        is NetworkError.Unauthorized -> return "Unauthorized. Please login."
    }
}