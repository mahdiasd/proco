package com.proco.data.repository

import com.proco.data.remote.api.ApiService
import com.proco.data.remote.utils.safeCall
import com.proco.domain.model.network.DataResult
import com.proco.domain.model.user.Job
import com.proco.domain.repository.JobRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JobRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : JobRepository {

    override suspend fun getJobs() = flow<DataResult<ImmutableList<Job>>> {
        safeCall { apiService.getJobs() }
    }

}


