package com.proco.domain.model.user

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable


@Serializable
@Stable
sealed class UserType {
    data object Mentee : UserType()
    data object Mentor : UserType()
}