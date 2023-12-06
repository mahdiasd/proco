package com.proco.domain.usecase.user

import com.proco.domain.data_store.UserDataStore
import com.proco.domain.model.network.DataResult
import com.proco.domain.model.user.User
import com.proco.domain.repository.UserRepository
import com.proco.domain.usecase.ResultUseCase
import com.proco.network.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val dataStore: UserDataStore,
    private val repo: UserRepository,
) : ResultUseCase<GetUserUseCase.DataSourceType, Flow<DataResult<User>>>(dispatcher) {

    sealed interface DataSourceType {
        data class Online(val id: Int) : DataSourceType
        data object Local : DataSourceType
    }

    override suspend fun doWork(params: DataSourceType): Flow<DataResult<User>> {
        return when(params){
            is DataSourceType.Local -> dataStore.readUser()
            is DataSourceType.Online -> repo.getUser(params.id)
        }
    }

}