package com.proco.data.di

import com.proco.data.model.ErrorHandler
import com.proco.data.model.ErrorHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideErrorHandler(): ErrorHandler {
        return ErrorHandlerImpl()
    }

}