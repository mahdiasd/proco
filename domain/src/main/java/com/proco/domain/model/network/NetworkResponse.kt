package com.proco.domain.model.network

data class NetworkResponse(
    val appException: String? = null,
    val message: Any? = null,
    val data: Any?
) {
    fun getMessage(): String {
        return when (message) {
            is List<*> -> message.joinToString(separator = "\n")
            is String -> message
            else -> ""
        }
    }
}
