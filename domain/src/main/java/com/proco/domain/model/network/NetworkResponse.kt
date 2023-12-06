package com.proco.domain.model.network

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Serializable
@Stable
data class NetworkResponse<T>(
    val status: String,
    val appException: String? = null,
    val message: String? = null,
    val data: T?
) {

    fun getsMessage(): String {
        return message.toString()
//        return when (message) {
//            is String -> message.joinToString(separator = "\\n")
//            is String -> message
//            else -> ""
//        }
    }

}