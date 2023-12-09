package com.proco.domain.model.user

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Skill(
    val name: String = ""
)