package com.proco.data.repository

import com.proco.data.mapper.ScheduleMapper
import com.proco.data.mapper.toSchedule
import com.proco.data.remote.api.ApiService
import com.proco.data.remote.utils.safeCall
import com.proco.domain.model.network.DataResult
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(private val apiService: ApiService, private val scheduleMapper: ScheduleMapper) : ScheduleRepository {
    override suspend fun saveSchedule(schedule: List<Schedule>) = flow {
        when (val result = safeCall { apiService.saveSchedule(scheduleMapper.mapFrom(schedule)) }) {
            is DataResult.Failure -> emit(DataResult.Failure(result.networkError))
            is DataResult.Success -> emit(DataResult.Success(true))
        }
    }

    override suspend fun getSchedule(id: Int) = flow {
        when (val result = safeCall { apiService.getSchedule(id) }) {
            is DataResult.Failure -> emit(DataResult.Failure(result.networkError))
            is DataResult.Success -> emit(DataResult.Success(result.data.toSchedule()))
        }
    }
}


