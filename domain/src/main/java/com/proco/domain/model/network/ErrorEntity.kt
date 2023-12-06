package com.proco.domain.model.network

sealed class ErrorEntity {
    data object IoException : ErrorEntity()
    data object NotFound : ErrorEntity()
    data object AccessDenied : ErrorEntity()
    data object ServerUnavailable : ErrorEntity()
    data class HttpException(val message: String) : ErrorEntity()
    data class Unknown(val message: String) : ErrorEntity()
}

fun ErrorEntity.getUiMessage(): String {
    return when (this) {
        ErrorEntity.AccessDenied -> "Access denied, please re-login"
        is ErrorEntity.HttpException -> return message
        ErrorEntity.IoException -> "Internet connection failed"
        ErrorEntity.NotFound -> "Page not found"
        ErrorEntity.ServerUnavailable -> "Server is busy, Please try latter"
        is ErrorEntity.Unknown -> return message
    }
}