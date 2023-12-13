package com.proco.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatar: String?,
    val sex: String,
    val type: String,
)