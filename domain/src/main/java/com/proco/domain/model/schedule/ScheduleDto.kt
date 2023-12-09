package com.proco.domain.model.schedule

import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable

@Serializable

data class ScheduleDto(
    val day: String,
    val hours: ImmutableList<Int>
)
