package com.proco.domain.usecase.country

import com.proco.domain.model.network.DataResult
import com.proco.domain.model.user.Country
import com.proco.domain.repository.CountryRepository
import com.proco.domain.usecase.ResultUseCase
import com.proco.network.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repo: CountryRepository
) : ResultUseCase<Unit, Flow<DataResult<List<Country>>>>(dispatcher) {

    override suspend fun doWork(params: Unit): Flow<DataResult<List<Country>>> {
        return repo.getCountries()
    }
}