package com.proco.data.repository

import com.proco.data.mapper.ScheduleMapper
import com.proco.data.remote.api.ApiService
import com.proco.data.remote.utils.safeCall
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(private val apiService: ApiService, private val scheduleMapper: ScheduleMapper) : ScheduleRepository {
    override suspend fun saveSchedule(schedule: List<Schedule>) = flow {
        emit(safeCall { apiService.saveSchedule(scheduleMapper.mapFrom(schedule)) })
    }
}


