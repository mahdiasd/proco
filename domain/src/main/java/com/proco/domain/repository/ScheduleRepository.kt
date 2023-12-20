package com.proco.domain.repository

import com.proco.domain.model.network.DataResult
import com.proco.domain.model.schedule.Schedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun saveSchedule(schedule: List<Schedule>): Flow<DataResult<Boolean>>
    suspend fun getSchedule(id: Int): Flow<DataResult<List<Schedule>>>
}