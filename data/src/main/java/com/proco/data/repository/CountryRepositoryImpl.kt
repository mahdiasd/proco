package com.proco.data.repository

import com.proco.data.remote.api.ApiService
import com.proco.data.remote.utils.safeCall
import com.proco.domain.model.network.DataResult
import com.proco.domain.model.user.Country
import com.proco.domain.repository.CountryRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CountryRepository {

    override suspend fun getCountries() = flow<DataResult<List<Country>>> {
        emit(safeCall { apiService.getCountries() })
    }

}


