package com.proco.domain.data_store

import com.proco.domain.model.network.DataResult
import com.proco.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserDataStore {
    suspend fun saveUser(filter: User)
    suspend fun readUser(): Flow<DataResult<User>>
}
