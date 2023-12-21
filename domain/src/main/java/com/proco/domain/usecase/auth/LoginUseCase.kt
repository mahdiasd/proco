package com.proco.domain.usecase.auth

import com.proco.domain.model.network.DataResult
import com.proco.domain.repository.UserRepository
import com.proco.domain.usecase.ResultUseCase
import com.proco.network.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) : ResultUseCase<LoginUseCase.LoginParam, Flow<DataResult<Unit>>>(dispatcher) {

    data class LoginParam(val email: String, val password: String)

    override suspend fun doWork(params: LoginParam): Flow<DataResult<Unit>> {
        return userRepository.login(params.email, params.password)
    }
}