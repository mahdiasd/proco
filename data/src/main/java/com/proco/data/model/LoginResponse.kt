package com.proco.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val user: UserDto,
    val token: String
)