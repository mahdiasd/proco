package com.proco.domain.model.schedule

import androidx.compose.runtime.Stable
import com.proco.domain.kotin_serializer.ImmutableListSerializer
import com.proco.domain.kotin_serializer.InstantSerializer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable
import java.time.Instant

@Stable
@Serializable
data class Schedule(
    @Serializable(InstantSerializer::class)
    val date: Instant,

    @Serializable(ImmutableListSerializer::class)
    val hours: ImmutableList<HourRange>,
)