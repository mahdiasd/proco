package com.proco.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val user: UserCacheResponse,
    val token: String
)