package com.proco.domain.data_store

import com.proco.domain.model.network.DataResult
import com.proco.domain.model.user.UserCache
import kotlinx.coroutines.flow.Flow

interface UserDataStore {
    suspend fun saveUser(user: UserCache)
    suspend fun readUser(): Flow<DataResult<UserCache>>
}
