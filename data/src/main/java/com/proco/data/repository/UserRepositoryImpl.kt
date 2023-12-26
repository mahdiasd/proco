package com.proco.data.repository

import com.proco.data.mapper.RegisterMapper
import com.proco.data.mapper.toUserCache
import com.proco.data.mapper.toUserSummaryList
import com.proco.data.remote.api.ApiService
import com.proco.data.remote.utils.safeCall
import com.proco.domain.data_store.UserDataStore
import com.proco.domain.encrypt_shared.TokenDataStore
import com.proco.domain.model.network.DataResult
import com.proco.domain.model.user.User
import com.proco.domain.model.user.UserCache
import com.proco.domain.repository.UserRepository
import com.proco.domain.usecase.auth.RegisterParam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val registerMapper: RegisterMapper,
    private val tokenDataStore: TokenDataStore,
    private val userDataStore: UserDataStore
) : UserRepository {

    override suspend fun login(email: String, password: String): Flow<DataResult<UserCache>> = flow {
        when (val result = safeCall { apiService.login(email, password) }) {
            is DataResult.Failure -> emit(DataResult.Failure(result.networkError))
            is DataResult.Success -> {
                tokenDataStore.saveToken(result.data.token)
                userDataStore.saveUser(result.data.user.toUserCache())
                emit(DataResult.Success(result.data.user.toUserCache()))
            }
        }
    }

    override suspend fun register(registerParam: RegisterParam): Flow<DataResult<UserCache>> = flow {
        when (val result = safeCall { apiService.register(registerMapper.mapFrom(registerParam)) }) {
            is DataResult.Failure -> {
                emit(DataResult.Failure(result.networkError))
            }

            is DataResult.Success -> {
                tokenDataStore.saveToken(result.data.token)
                userDataStore.saveUser(result.data.user.toUserCache())
                emit(DataResult.Success(result.data.user.toUserCache()))
            }
        }
    }

    override suspend fun getUsers(search: String?, jobTitle: String?, country: String?, page: Int) = flow {
        when (val result = safeCall { apiService.getUsers(search?.ifEmpty { null }, jobTitle, country, page) }) {
            is DataResult.Failure -> emit(DataResult.Failure(result.networkError))
            is DataResult.Success -> emit(DataResult.Success(result.data.toUserSummaryList()))
        }
    }


    override suspend fun getUser(id: Int) = flow {
        emit(safeCall { apiService.getUser(id) })
    }


    override suspend fun updatePrice(price: Int) = flow {
        when (val result = safeCall { apiService.updatePrice(price) }) {
            is DataResult.Failure -> {
                emit(DataResult.Failure(result.networkError))
            }

            is DataResult.Success -> {
                userDataStore.saveUser(result.data.toUserCache())
                emit(DataResult.Success(result.data.toUserCache()))
            }
        }
    }

    override suspend fun updateUser(user: User): Flow<DataResult<User>> {
        TODO("Not yet implemented")
    }


}


