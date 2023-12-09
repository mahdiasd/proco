package com.proco.domain.repository

import com.proco.domain.model.network.DataResult
import com.proco.domain.model.user.Job
import kotlinx.coroutines.flow.Flow

interface JobRepository {
    suspend fun getJobs(): Flow<DataResult<List<Job>>>
}