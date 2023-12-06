package com.proco.domain.model.schedule

import kotlinx.collections.immutable.ImmutableList

data class ScheduleDto(
    val day: String,
    val hours: ImmutableList<Int>
)
