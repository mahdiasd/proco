package com.proco.domain.data_store

import com.proco.domain.model.filter.UserFilter
import kotlinx.coroutines.flow.Flow

interface UserFilterDataStore {
    suspend fun saveFilter(filter: UserFilter)
    suspend fun readFilter(): Flow<UserFilter?>
}
