package com.proco.data.repository

import com.proco.data.mapper.RegisterMapper
import com.proco.data.remote.api.ApiService
import com.proco.data.remote.utils.safeCall
import com.proco.domain.model.network.DataResult
import com.proco.domain.model.user.User
import com.proco.domain.repository.UserRepository
import com.proco.domain.usecase.auth.RegisterParam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val registerMapper: RegisterMapper,
//    private val tokenDataStore: TokenDataStore
) : UserRepository {

    override suspend fun login(email: String, password: String): Flow<DataResult<Unit>> = flow {
        val result = safeCall { apiService.login(email, password) }
        when (result) {
            is DataResult.Failure -> {
                emit(DataResult.Failure(result.networkError))
            }

            is DataResult.Success -> {
//                tokenDataStore.saveToken(result.data.token)
                emit(DataResult.Success(Unit))
            }
        }
    }

    override suspend fun getUsers(search: String?, jobTitle: String?, country: String?, page: Int) = flow {
        emit(safeCall { apiService.getUsers(search, jobTitle, country, page) })
    }

    override suspend fun getUser(id: Int) = flow {
        emit(safeCall { apiService.getUser(id) })
    }

    override suspend fun register(registerParam: RegisterParam) = flow {
        emit(safeCall { apiService.register(registerMapper.mapFrom(registerParam)) })
    }

    override suspend fun updatePrice(price: Int) = flow<DataResult<User>> {
        emit(safeCall { apiService.updatePrice(price) })
    }

    override suspend fun updateUser(user: User): Flow<DataResult<User>> {
        TODO("Not yet implemented")
    }


}


