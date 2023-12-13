package com.proco.domain.model.schedule

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleDto(
    val start: Long,
    val end: Long,
)
