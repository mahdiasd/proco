package com.proco.domain.repository

import com.proco.domain.model.network.DataResult
import com.proco.domain.model.schedule.ScheduleDto
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun saveSchedule(scheduleDtos: List<ScheduleDto>): Flow<DataResult<Boolean>>
}