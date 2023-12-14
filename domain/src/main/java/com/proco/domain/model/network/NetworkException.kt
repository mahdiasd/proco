package com.proco.domain.model.network

import androidx.compose.runtime.Stable
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
@Stable
data class NetworkException(
    val statusCode: Int?,
    val error: String? = null,
    @Contextual val message: Any? = null,
) {

    fun getParseMessage(): String {
        return when (message) {
            is List<*> -> message.joinToString(separator = "\n")
            is String -> message
            else -> ""
        }
    }

}

