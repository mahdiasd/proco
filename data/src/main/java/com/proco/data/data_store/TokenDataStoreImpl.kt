package com.proco.data.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.proco.domain.data_store.TokenDataStore
import com.proco.domain.model.network.DataResult
import com.proco.domain.model.network.NetworkError
import com.proco.extention.safeDecode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TokenDataStoreImpl @Inject constructor(private val dataStore: DataStore<Preferences>) : TokenDataStore {
    companion object {
        val tokenKey = stringPreferencesKey("user")
    }

    override suspend fun saveToken(token: String) {
        dataStore.edit { preference ->
            preference[tokenKey] = Json.encodeToString(token)
        }
    }

    override suspend fun readToken(): Flow<DataResult<String>> {
        return dataStore.data.map {
            val token = Json.safeDecode<String>(it[tokenKey])
            if (token == null) DataResult.Failure(NetworkError.AccessDenied)
            else DataResult.Success(token)
        }
    }
}
