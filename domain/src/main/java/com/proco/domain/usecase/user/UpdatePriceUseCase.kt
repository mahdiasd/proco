package com.proco.domain.usecase.user

import com.proco.domain.model.network.DataResult
import com.proco.domain.model.user.User
import com.proco.domain.repository.UserRepository
import com.proco.domain.usecase.ResultUseCase
import com.proco.network.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdatePriceUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repo: UserRepository
) : ResultUseCase<Int, Flow<DataResult<User>>>(dispatcher) {

    override suspend fun doWork(params: Int): Flow<DataResult<User>> {
        return repo.updatePrice(params)
    }

}