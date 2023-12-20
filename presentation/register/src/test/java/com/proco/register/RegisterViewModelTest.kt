package com.proco.register

import com.proco.domain.model.user.Expertise
import com.proco.domain.usecase.auth.RegisterUseCase
import com.proco.domain.usecase.country.GetCountriesUseCase
import com.proco.domain.usecase.job.GetJobsUseCase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {
    private val registerUseCase: RegisterUseCase = mock()
    private val getCountriesUseCase: GetCountriesUseCase = mock()
    private val getJobsUseCase: GetJobsUseCase = mock()

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `onExpertise adds expertise to the list`(): Unit = runBlocking {
        launch {
            val vm: RegisterViewModel = RegisterViewModel(registerUseCase, getCountriesUseCase, getJobsUseCase)
            val expertise = Expertise(id = 1, name = "Test Expertise")

            // When
            vm.onTriggerEvent(RegisterUiEvent.OnExpertise(expertise))

            // Then
            assert(vm.uiState.value.data.expertises?.contains(expertise) == true)
        }
    }
    @Test
    fun `onRemoveExpertise removes expertise from the list`(): Unit = runBlocking {
        launch {
            val vm: RegisterViewModel = RegisterViewModel(registerUseCase, getCountriesUseCase, getJobsUseCase)
            val expertise = Expertise(id = 1, name = "Test Expertise")

            // When
            vm.onTriggerEvent(RegisterUiEvent.OnExpertise(expertise))

            // Then
            assert(vm.uiState.value.data.expertises?.contains(expertise) == true)

            vm.onTriggerEvent(RegisterUiEvent.OnRemoveExpertise(expertise))

            assert(vm.uiState.value.data.expertises?.contains(expertise) == false)
        }
    }

}