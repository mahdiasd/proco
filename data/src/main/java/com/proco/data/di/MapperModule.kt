package com.proco.data.di

import com.proco.data.mapper.RegisterMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Singleton
    @Provides
    fun provideRegisterMapper(): RegisterMapper {
        return RegisterMapper()
    }

}