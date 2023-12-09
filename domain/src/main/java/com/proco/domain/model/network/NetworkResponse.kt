package com.proco.domain.model.network

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Serializable
@Stable
data class NetworkResponse<T>(
    val status: String?,
    val appException: String? = null,
    val message: String? = null,
    val messages: List<String>? = null,
    val data: T?
) {

    fun getParseMessage(): String {
        return if (!messages.isNullOrEmpty()) messages.joinToString { "$it\n" } else if (!message.isNullOrEmpty()) message else ""
    }

}

