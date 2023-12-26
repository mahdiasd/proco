package com.proco.data.encrypt_shared

import android.content.SharedPreferences
import com.proco.domain.encrypt_shared.UserIdRepository
import javax.inject.Inject

class UserIdRepositoryImpl @Inject constructor(private val encryptSharedPreferences: SharedPreferences) : UserIdRepository {
    override fun saveId(id: String) {
        encryptSharedPreferences.edit().putString("user-id", id).apply()
    }

    override fun readId(): String {
        return encryptSharedPreferences.getString("user-id", "") ?: ""
    }
}
