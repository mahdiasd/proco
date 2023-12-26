package com.proco.domain.usecase.user

import com.proco.domain.data_store.UserDataStore
import com.proco.domain.model.network.DataResult
import com.proco.domain.model.user.UserCache
import com.proco.domain.usecase.ResultUseCase
import com.proco.network.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalUserUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val dataStore: UserDataStore,
) : ResultUseCase<Unit, Flow<DataResult<UserCache>>>(dispatcher) {

    override suspend fun doWork(params: Unit): Flow<DataResult<UserCache>> {
        return dataStore.readUser()
    }

}