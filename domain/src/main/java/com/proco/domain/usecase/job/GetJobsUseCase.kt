package com.proco.domain.usecase.job

import com.proco.domain.model.network.DataResult
import com.proco.domain.model.user.Job
import com.proco.domain.repository.JobRepository
import com.proco.domain.usecase.ResultUseCase
import com.proco.network.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetJobsUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repo: JobRepository
) : ResultUseCase<Unit, Flow<DataResult<List<Job>>>>(dispatcher) {

    override suspend fun doWork(params: Unit): Flow<DataResult<List<Job>>> {
        return repo.getJobs()
    }
}