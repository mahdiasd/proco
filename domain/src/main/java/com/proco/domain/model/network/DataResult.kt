package com.proco.domain.model.network

sealed class DataResult<T> {
    data class Success<T>(val data: T) : DataResult<T>()
    data class Failure<T>(val errorEntity: ErrorEntity) : DataResult<T>()
}
