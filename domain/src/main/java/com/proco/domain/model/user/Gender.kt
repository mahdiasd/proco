package com.proco.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
enum class Gender {
    Male,
    Female,
    NonBinary,
    Unknown,
}

fun Gender.apiName(): String {
    return this.name.uppercase()
}
fun Gender.uiName(): String {
    return this.name.uppercase()
}
