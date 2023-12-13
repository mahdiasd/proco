package com.proco.schedule

import androidx.compose.runtime.mutableStateListOf
import com.proco.domain.model.schedule.HourRange
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.usecase.ScheduleUseCase
import kotlinx.collections.immutable.toImmutableList
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import java.time.Instant

class ScheduleViewModelTest {
    var scheduleUseCase: ScheduleUseCase = mock()
    var vm: ScheduleViewModel = ScheduleViewModel(scheduleUseCase)

    @Test
    fun `remove hour from schedule`() {

        // Test data
        val date = Instant.now()
        val hourRangeToRemove = HourRange(Instant.MIN, Instant.MAX)
        val schedule = Schedule(date, listOf(hourRangeToRemove).toImmutableList())
        val localSchedule = mutableStateListOf(schedule)
        // Set up view model

        // Set initial state
        vm.setState { vm.currentState.copy(localSchedule = localSchedule) }

        // Verify initial state
        assertTrue(vm.currentState.localSchedule == localSchedule)

        // Trigger remove hour event
        vm.onTriggerEvent(ScheduleUiEvent.OnRemoveHour(date, hourRangeToRemove))

        // Verify hour was removed
        assertTrue(vm.currentState.localSchedule[0].hours.size == 0)
    }
}