package com.proco.domain.model.network

import androidx.compose.runtime.Stable
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement

@Serializable
@Stable
data class NetworkException(
    val statusCode: Int?,
    val error: String? = null,
    @Contextual val message: JsonElement? = null,
) {

    fun getParseMessage(): String {
        return when (message) {
            is JsonArray -> {
                message.joinToString { "\n" }
            }

            is JsonElement -> {
                message.toString()
            }

            else -> {
                ""
            }
        }
    }
}
