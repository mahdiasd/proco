package com.proco.domain.model.user

import kotlinx.serialization.Serializable

@Serializable

data class Country  (
    val id: Int = 0,
    val code: String,
    val name: String,
)