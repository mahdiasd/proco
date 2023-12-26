package com.proco.domain.model.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleDto(
    @SerialName("start_time") val start: String,
    @SerialName("end_time") val end: String,
)
