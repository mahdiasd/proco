package com.proco.domain.model.network

sealed class NetworkError {
    data object IoException : NetworkError()
    data object NotFound : NetworkError()
    data object AccessDenied : NetworkError()
    data object Unknown : NetworkError()
    data object IllegalArgumentException : NetworkError()
    data object ServerUnavailable : NetworkError()
    data class HttpException(val message: String) : NetworkError()
}

fun NetworkError.getUiMessage(): String {
    return when (this) {
        is NetworkError.AccessDenied -> "Access denied, please re-login"
        is NetworkError.HttpException -> return message
        is NetworkError.IoException -> "There was a problem connecting to the server. Please check your internet connection and try again. If the issue persists, contact support."
        is NetworkError.NotFound -> "The requested resource could not be found. Please check the details and try again. If the problem continues, contact support."
        is NetworkError.ServerUnavailable -> "Sorry, the server is currently unavailable. Please try again later. If the issue persists, contact support."
        is NetworkError.Unknown -> return "An unexpected error occurred. Please try again later. If the problem persists, contact support for assistance."
        is NetworkError.IllegalArgumentException -> "Oops! Something unexpected happened. Please check your input and try again. If the issue persists, contact support."
    }
}