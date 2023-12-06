package com.proco.domain.usecase.filter

import com.proco.domain.data_store.UserFilterDataStore
import com.proco.domain.model.filter.UserFilter
import com.proco.domain.usecase.ResultUseCase
import com.proco.network.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SaveUserFilterUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repo: UserFilterDataStore
) : ResultUseCase<UserFilter, Unit>(dispatcher) {

    override suspend fun doWork(params: UserFilter) {
        repo.saveFilter(params)
    }

}