package com.proco.domain.model.schedule

import androidx.compose.runtime.Stable
import com.proco.domain.kotin_serializer.ImmutableListSerializer
import com.proco.domain.kotin_serializer.LocalDateSerializer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Stable
@Serializable
data class Schedule(
    @Serializable(LocalDateSerializer::class)
    val date: LocalDate,

    @Serializable(ImmutableListSerializer::class)
    val hours: ImmutableList<HourRange>,
)