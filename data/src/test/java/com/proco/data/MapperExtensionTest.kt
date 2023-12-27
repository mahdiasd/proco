package com.proco.data

import com.proco.data.mapper.toSchedule
import com.proco.domain.model.schedule.HourRange
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.model.schedule.ScheduleDto
import com.proco.extention.toInstant
import com.proco.extention.toLocalDate
import kotlinx.collections.immutable.toImmutableList
import org.junit.Assert.assertTrue
import org.junit.Test

class MapperExtensionTest {
    @Test
    fun `list of scheduleDto to list of schedule`() {
        val scheduleDtoList = listOf(
            ScheduleDto("2023-12-27T07:31:18.350Z", "2023-12-27T07:46:00.000Z"),
            ScheduleDto("2023-12-27T11:30:00.000Z", "2023-12-27T13:30:00.000Z"),
            ScheduleDto("2023-12-28T08:00:00.000Z", "2023-12-28T08:15:00.000Z"),
        )

        val expectedSchedule = listOf(
            Schedule(
                date = "2023-12-27T07:31:18.350Z".toInstant().toLocalDate(),
                hours = listOf(
                    HourRange("2023-12-27T07:31:18.350Z".toInstant(), "2023-12-27T07:46:00.000Z".toInstant()),
                    HourRange("2023-12-27T11:30:00.000Z".toInstant(), "2023-12-27T13:30:00.000Z".toInstant()),
                ).toImmutableList()
            ),
            Schedule(
                date = "2023-12-28T08:00:00.000Z".toInstant().toLocalDate(),
                hours = listOf(
                    HourRange("2023-12-28T08:00:00.000Z".toInstant(), "2023-12-28T08:15:00.000Z".toInstant()),
                ).toImmutableList()
            )
        )

        val scheduleList = scheduleDtoList.toSchedule()
        for (i in scheduleList.indices) {
            assertTrue(scheduleList[i].date.compareTo(expectedSchedule[i].date) == 0)

            for (j in expectedSchedule[i].hours.indices) {
                assertTrue(scheduleList[i].hours[j].start.toEpochMilli() == expectedSchedule[i].hours[j].start.toEpochMilli())
                assertTrue(scheduleList[i].hours[j].end.toEpochMilli() == expectedSchedule[i].hours[j].end.toEpochMilli())
            }
        }
    }
}