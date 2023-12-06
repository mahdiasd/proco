package com.proco.domain.model.network

sealed class ErrorEntity {
    data object IoException : ErrorEntity()
    data object NotFound : ErrorEntity()
    data object AccessDenied : ErrorEntity()
    data object Unknown : ErrorEntity()
    data object IllegalArgumentException : ErrorEntity()
    data object ServerUnavailable : ErrorEntity()
    data class HttpException(val message: String) : ErrorEntity()
}

fun ErrorEntity.getUiMessage(): String {
    return when (this) {
        is ErrorEntity.AccessDenied -> "Access denied, please re-login"
        is ErrorEntity.HttpException -> return message
        is ErrorEntity.IoException -> "There was a problem connecting to the server. Please check your internet connection and try again. If the issue persists, contact support."
        is ErrorEntity.NotFound -> "The requested resource could not be found. Please check the details and try again. If the problem continues, contact support."
        is ErrorEntity.ServerUnavailable -> "Sorry, the server is currently unavailable. Please try again later. If the issue persists, contact support."
        is ErrorEntity.Unknown -> return "An unexpected error occurred. Please try again later. If the problem persists, contact support for assistance."
        is ErrorEntity.IllegalArgumentException -> "Oops! Something unexpected happened. Please check your input and try again. If the issue persists, contact support."
    }
}