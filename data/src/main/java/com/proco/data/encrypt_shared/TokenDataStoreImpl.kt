package com.proco.data.encrypt_shared

import android.content.SharedPreferences
import com.proco.domain.encrypt_shared.TokenDataStore
import javax.inject.Inject

class TokenDataStoreImpl @Inject constructor(private val encryptSharedPreferences: SharedPreferences) : TokenDataStore {
    override fun saveToken(token: String) {
        encryptSharedPreferences.edit().putString("token", token).apply()
    }

    override fun readToken(): String {
        return encryptSharedPreferences.getString("token", "") ?: ""
    }
}
