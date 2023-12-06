package com.proco.domain.usecase

import com.proco.domain.model.network.DataResult
import com.proco.domain.model.user.User
import com.proco.domain.repository.UserRepository
import com.proco.network.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMentorListUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repo: UserRepository
) : ResultUseCase<GetMentorListUseCase.GetMentorListParam, Flow<DataResult<List<User>>>>(dispatcher) {

    data class GetMentorListParam(
        val search: String,
        val jobTitle: String?,
        val country: String?,
        val page: Int
    )

    override suspend fun doWork(params: GetMentorListParam): Flow<DataResult<List<User>>> {
        return repo.getUsers(params.search, params.jobTitle, params.country, params.page)
    }

}