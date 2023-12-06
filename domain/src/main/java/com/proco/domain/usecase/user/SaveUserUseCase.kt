package com.proco.domain.usecase.user

import com.proco.domain.data_store.UserDataStore
import com.proco.domain.model.user.User
import com.proco.domain.usecase.ResultUseCase
import com.proco.network.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repo: UserDataStore
) : ResultUseCase<User, Unit>(dispatcher) {

    override suspend fun doWork(params: User) {
        repo.saveUser(params)
    }

}