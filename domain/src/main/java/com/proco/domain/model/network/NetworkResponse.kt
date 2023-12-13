package com.proco.domain.model.network

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Serializable
@Stable
data class NetworkResponse<T>(
    val status: String?,
    val appException: String? = null,
    val message: List<String>? = null,
//    val messages: List<String>? = null,
    val data: T?
) {

    fun getParseMessage(): String {
        return message?.let { message ->
            message.joinToString { "$it\n" }
        } ?: run { "" }
    }

}

