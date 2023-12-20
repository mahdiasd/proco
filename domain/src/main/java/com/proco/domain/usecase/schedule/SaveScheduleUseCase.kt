package com.proco.domain.usecase.schedule

import com.proco.domain.model.network.DataResult
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.repository.ScheduleRepository
import com.proco.domain.usecase.ResultUseCase
import com.proco.network.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveScheduleUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repo: ScheduleRepository,
) : ResultUseCase<List<Schedule>, Flow<DataResult<Boolean>>>(dispatcher) {

    override suspend fun doWork(params: List<Schedule>): Flow<DataResult<Boolean>> {
        return repo.saveSchedule(params)
    }

}