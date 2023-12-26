package com.proco.domain.encrypt_shared

interface TokenDataStore {
    fun saveToken(token: String)
    fun readToken(): String
}
