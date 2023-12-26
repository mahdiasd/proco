package com.proco.domain.model.user

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable


@Serializable
@Stable
sealed class UserType {
    @Serializable
    data object Mentee : UserType()
    @Serializable
    data object Mentor : UserType()
}

fun UserType.apiName(): String {
    return this.toString().uppercase()
}