package com.proco.schedule

import com.proco.domain.model.schedule.HourRange
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.usecase.schedule.GetScheduleUseCase
import com.proco.domain.usecase.schedule.SaveScheduleUseCase
import com.proco.domain.usecase.user.GetLocalUserUseCase
import com.proco.domain.usecase.user.UpdatePriceUseCase
import com.proco.extention.toLocalDate
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import java.time.Instant

@OptIn(ExperimentalCoroutinesApi::class)

class ScheduleViewModelTest {
    private var saveScheduleUseCase: SaveScheduleUseCase = mock()
    private var getScheduleUseCase: GetScheduleUseCase = mock()
    private var updatePriceUseCase: UpdatePriceUseCase = mock()
    private var getLocalUserUseCase: GetLocalUserUseCase = mock()


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
    fun `remove hour from schedule`() = runBlocking {
        val vm = ScheduleViewModel(saveScheduleUseCase = saveScheduleUseCase, updatePriceUseCase = updatePriceUseCase, getScheduleUseCase = getScheduleUseCase, getLocalUserUseCase = getLocalUserUseCase)

        // Test data
        val date = Instant.now()
        val hourRangeToRemove = HourRange(Instant.MIN, Instant.MAX)
        val schedule = Schedule(date.toLocalDate(), listOf(hourRangeToRemove).toImmutableList())

        vm.onTriggerEvent(ScheduleUiEvent.OnAddHour(date, hourRangeToRemove))

        // Verify initial state
        assertTrue(vm.currentState.localSchedule.size == 1)

        // Trigger remove hour event
        vm.onTriggerEvent(ScheduleUiEvent.OnRemoveHour(date, hourRangeToRemove))

        // Verify hour was removed
        assertTrue(vm.currentState.localSchedule.size == 0)
    }
}