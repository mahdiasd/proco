package com.proco.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
enum class Gender {
    Male,
    Female,
    NonBinary;

    override fun toString(): String {
        return super.name.lowercase()
    }

}

