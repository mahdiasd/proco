package com.proco.data.di

import com.proco.data.encrypt_shared.UserIdRepositoryImpl
import com.proco.data.repository.CountryRepositoryImpl
import com.proco.data.repository.JobRepositoryImpl
import com.proco.data.repository.ScheduleRepositoryImpl
import com.proco.data.repository.UserRepositoryImpl
import com.proco.domain.encrypt_shared.UserIdRepository
import com.proco.domain.repository.CountryRepository
import com.proco.domain.repository.JobRepository
import com.proco.domain.repository.ScheduleRepository
import com.proco.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindRepositories {

    @Binds
    @Singleton
    fun bindUserRepo(repository: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun bindScheduleRepo(repository: ScheduleRepositoryImpl): ScheduleRepository

    @Binds
    @Singleton
    fun bindCountryRepo(repository: CountryRepositoryImpl): CountryRepository

    @Binds
    @Singleton
    fun bindJobRepo(repository: JobRepositoryImpl): JobRepository

    @Binds
    @Singleton
    fun bindUserIdRepo(repository: UserIdRepositoryImpl): UserIdRepository
}