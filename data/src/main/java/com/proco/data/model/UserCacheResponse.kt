package com.proco.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserCacheResponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val type: String,
    val cost: String?,
)