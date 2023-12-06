package com.proco.data.repository

import com.proco.data.remote.api.ApiService
import com.proco.data.remote.utils.safeCall
import com.proco.domain.model.network.DataResult
import com.proco.domain.model.schedule.ScheduleDto
import com.proco.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(private val apiService: ApiService) : ScheduleRepository {
    override suspend fun saveSchedule(scheduleDtos: List<ScheduleDto>) = flow<DataResult<Boolean>> {
        safeCall { apiService.saveSchedule(scheduleDtos) }
    }
}


