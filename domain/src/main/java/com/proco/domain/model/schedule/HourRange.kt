package com.proco.domain.model.schedule

import androidx.compose.runtime.Stable
import com.proco.domain.kotin_serializer.InstantSerializer
import kotlinx.serialization.Serializable
import java.time.Instant


@Stable
@Serializable
data class HourRange(
    @Serializable(InstantSerializer::class)
    val start: Instant,
    @Serializable(InstantSerializer::class)
    val end: Instant,
)