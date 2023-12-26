package com.proco.domain.repository

import com.proco.domain.model.network.DataResult
import com.proco.domain.model.user.User
import com.proco.domain.model.user.UserCache
import com.proco.domain.model.user.UserSummary
import com.proco.domain.usecase.auth.RegisterParam
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(email: String, password: String): Flow<DataResult<UserCache>>
    suspend fun getUsers(search: String? = null, jobTitle: String? = null, country: String? = null, page: Int): Flow<DataResult<List<UserSummary>>>
    suspend fun getUser(id: Int): Flow<DataResult<User>>
    suspend fun register(registerParam: RegisterParam): Flow<DataResult<UserCache>>
    suspend fun updatePrice(price: Int): Flow<DataResult<UserCache>>
    suspend fun updateUser(user: User): Flow<DataResult<User>>
}