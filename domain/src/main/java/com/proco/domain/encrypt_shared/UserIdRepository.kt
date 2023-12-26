package com.proco.domain.encrypt_shared

interface UserIdRepository {
    fun saveId(id: String)
    fun readId(): String
}
