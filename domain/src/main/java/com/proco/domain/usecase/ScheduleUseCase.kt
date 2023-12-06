package com.proco.domain.usecase

import com.proco.domain.model.network.DataResult
import com.proco.domain.model.schedule.ScheduleDto
import com.proco.domain.repository.ScheduleRepository
import com.proco.network.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScheduleUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repo: ScheduleRepository
) : ResultUseCase<List<ScheduleDto>, Flow<DataResult<Boolean>>>(dispatcher) {

    override suspend fun doWork(params: List<ScheduleDto>): Flow<DataResult<Boolean>> {
        return repo.saveSchedule(params)
    }

}