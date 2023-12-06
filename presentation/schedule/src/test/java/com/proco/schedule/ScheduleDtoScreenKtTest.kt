package com.proco.schedule

import com.proco.domain.model.schedule.DayName
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.model.time.HoursOfDay
import com.proco.schedule.utils.ScheduleUtils
import kotlinx.collections.immutable.toImmutableList
import org.junit.Assert.*
import org.junit.Test

class ScheduleScreenKtTest {
    @Test
    fun `check is schedule returns true if schedule exists for day`() {
        val hours = listOf(HoursOfDay("", 0), HoursOfDay("", 1), HoursOfDay("", 2)).toImmutableList()
        val schedule = listOf(
            Schedule(dayName = DayName.Saturday, hours = hours),
            Schedule(dayName = DayName.Sunday, hours = hours),
            Schedule(dayName = DayName.Monday, hours = hours),
        ).toImmutableList()

        val hoursOfDay = HoursOfDay(id = 0, name = "00:00 - 01:00")

        val result = ScheduleUtils.checkIsSchedule(schedule, 0, hoursOfDay)
        assertTrue(result)
    }

    @Test
    fun `check is schedule returns false if schedule does not exist for day`() {
        val hours = listOf(HoursOfDay("", 0), HoursOfDay("", 1), HoursOfDay("", 2)).toImmutableList()
        val schedule = listOf(
            Schedule(dayName = DayName.Saturday, hours = hours),
            Schedule(dayName = DayName.Sunday, hours = hours),
            Schedule(dayName = DayName.Monday, hours = hours),
        ).toImmutableList()

        val hoursOfDay = HoursOfDay(id = 3, name = "00:00 - 01:00")

        val result = ScheduleUtils.checkIsSchedule(schedule, 2, hoursOfDay)

        assertFalse(result)
    }

    @Test
    fun `check is schedule returns false if no schedule for day`() {
        val hours = listOf(HoursOfDay("", 0), HoursOfDay("", 1), HoursOfDay("", 2)).toImmutableList()
        val schedule = listOf(
            Schedule(dayName = DayName.Saturday, hours = hours),
            Schedule(dayName = DayName.Sunday, hours = hours),
            Schedule(dayName = DayName.Monday, hours = hours),
        ).toImmutableList()

        val hoursOfDay = HoursOfDay(id = 0, name = "00:00 - 01:00")

        val result = ScheduleUtils.checkIsSchedule(schedule, 3, hoursOfDay)

        assertFalse(result)
    }
}