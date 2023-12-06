package com.proco.domain.usecase.filter

import com.proco.domain.data_store.UserFilterDataStore
import com.proco.domain.model.filter.UserFilter
import com.proco.domain.usecase.ResultUseCase
import com.proco.network.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserFilterUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repo: UserFilterDataStore
) : ResultUseCase<Unit, Flow<UserFilter?>>(dispatcher) {

    override suspend fun doWork(params: Unit): Flow<UserFilter?> {
        return repo.readFilter()
    }

}