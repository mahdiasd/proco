package com.proco.domain.repository

import com.proco.domain.model.network.DataResult
import com.proco.domain.model.user.Country
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    suspend fun getCountries(): Flow<DataResult<ImmutableList<Country>>>
}