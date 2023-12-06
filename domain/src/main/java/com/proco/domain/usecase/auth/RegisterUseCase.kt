package com.proco.domain.usecase.auth

import com.proco.domain.model.network.DataResult
import com.proco.domain.model.user.User
import com.proco.domain.repository.UserRepository
import com.proco.domain.usecase.ResultUseCase
import com.proco.network.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository
) : ResultUseCase<RegisterParam, Flow<DataResult<User>>>(dispatcher) {

    override suspend fun doWork(params: RegisterParam): Flow<DataResult<User>> {
        return userRepository.register(params)
    }
}