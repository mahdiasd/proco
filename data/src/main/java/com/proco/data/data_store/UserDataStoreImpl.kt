package com.proco.data.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.proco.domain.data_store.UserDataStore
import com.proco.domain.model.network.DataResult
import com.proco.domain.model.network.NetworkError
import com.proco.domain.model.user.User
import com.proco.extention.safeDecode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class UserDataStoreImpl @Inject constructor(private val dataStore: DataStore<Preferences>) : UserDataStore {
    companion object {
        val userFilterKey = stringPreferencesKey("user")
    }

    override suspend fun saveUser(filter: User) {
        dataStore.edit { preference ->
            preference[userFilterKey] = Json.encodeToString(filter)
        }
    }

    override suspend fun readUser(): Flow<DataResult<User>> {
        return dataStore.data.map {
            val user = Json.safeDecode<User>(it[userFilterKey])
            if (user == null) DataResult.Failure(NetworkError.AccessDenied)
            else DataResult.Success(user)
        }
    }
}
