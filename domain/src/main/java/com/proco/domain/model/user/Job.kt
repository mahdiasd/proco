package com.proco.domain.model.user

import androidx.compose.runtime.Stable
import com.proco.domain.ImmutableListSerializer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Stable
data class Job(
    val id: Int = 0,
    val name: String,

    @SerialName("Expertises")
    @Serializable(with = ImmutableListSerializer::class)
    val expertises: ImmutableList<Expertise>
)