package com.proco.domain.model.user

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable

@Serializable
@Stable
data class Job(
    val id: Int = 0,
    val name: String,
    val expertises: ImmutableList<Expertise>
)