package com.proco.domain.usecase.schedule

import com.proco.domain.model.network.DataResult
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.repository.ScheduleRepository
import com.proco.domain.usecase.ResultUseCase
import com.proco.network.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScheduleUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repo: ScheduleRepository,
) : ResultUseCase<Int, Flow<DataResult<List<Schedule>>>>(dispatcher) {

    override suspend fun doWork(params: Int): Flow<DataResult<List<Schedule>>> {
        return repo.getSchedule(params)
    }

}