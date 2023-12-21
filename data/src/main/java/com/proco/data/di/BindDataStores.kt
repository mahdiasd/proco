package com.proco.data.di

import com.proco.data.data_store.TokenDataStoreImpl
import com.proco.data.data_store.UserDataStoreImpl
import com.proco.data.data_store.UserFilterDataStoreImpl
import com.proco.domain.data_store.TokenDataStore
import com.proco.domain.data_store.UserDataStore
import com.proco.domain.data_store.UserFilterDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class BindDataStores {

    @Binds
    abstract fun bindUserFilter(impl: UserFilterDataStoreImpl): UserFilterDataStore

    @Binds
    abstract fun bindUser(impl: UserDataStoreImpl): UserDataStore

    @Binds
    abstract fun bindToken(impl: TokenDataStoreImpl): TokenDataStore
}