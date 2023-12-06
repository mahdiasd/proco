package com.proco.base

sealed class UiResult<T>() {
    class Success<T>(val data: T) : UiResult<T>()
    class Loading<out> : UiResult<out>()
    data class Error<T>(val message: String) : UiResult<T>()
}