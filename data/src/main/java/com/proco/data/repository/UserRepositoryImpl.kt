package com.proco.data.repository

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
    private val apiService: ApiService
) : UserRepository {

    override suspend fun login(email: String, password: String): Flow<DataResult<User>> = flow {
        safeCall { apiService.login(email, password) }
    }

    override suspend fun getUsers(search: String?, jobTitle: String?, country: String?, page: Int) = flow<DataResult<List<User>>> {
        safeCall { apiService.getUsers(search, jobTitle, country, page) }
    }

    override suspend fun getUser(id: Int) = flow<DataResult<User>> {
        safeCall { apiService.getUser(id) }
    }

    override suspend fun register(registerParam: RegisterParam) = flow<DataResult<User>> {
        safeCall { apiService.register(registerParam) }
    }


}


