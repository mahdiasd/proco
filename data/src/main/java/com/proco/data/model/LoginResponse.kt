package com.proco.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val user: UserCacheResponse,
    val token: String
)