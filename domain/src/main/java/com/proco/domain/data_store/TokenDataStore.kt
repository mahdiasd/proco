package com.proco.domain.data_store

import com.proco.domain.model.network.DataResult
import kotlinx.coroutines.flow.Flow

interface TokenDataStore {
    suspend fun saveToken(token: String)
    suspend fun readToken(): Flow<DataResult<String>>
}
