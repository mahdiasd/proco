package com.proco.data.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.proco.domain.data_store.UserFilterDataStore
import com.proco.domain.model.filter.UserFilter
import com.proco.extention.safeDecode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class UserFilterDataStoreImpl @Inject constructor(private val dataStore: DataStore<Preferences>) : UserFilterDataStore {
    companion object {
        val userFilterKey = stringPreferencesKey("user_filter")
    }

    override suspend fun saveFilter(filter: UserFilter) {
        dataStore.edit { preference ->
            preference[userFilterKey] = Json.encodeToString(filter)
        }
    }

    override suspend fun readFilter(): Flow<UserFilter?> {
        return dataStore.data.map {
            Json.safeDecode(it[userFilterKey])
        }
    }
}
