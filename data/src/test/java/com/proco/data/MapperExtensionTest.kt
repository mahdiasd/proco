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
import java.time.LocalDateTime

class MapperExtensionTest {
    @Test
    fun `list of scheduleDto to list of schedule`() {
        val oneStart = LocalDateTime.of(2023, 12, 20, 15, 10, 0)
        val oneEnd = LocalDateTime.of(2023, 12, 20, 15, 50, 0)

        val twoStart = LocalDateTime.of(2023, 12, 20, 17, 10, 0)
        val twoEnd = LocalDateTime.of(2023, 12, 20, 18, 50, 0)

        val threeStart = LocalDateTime.of(2023, 12, 21, 10, 10, 0)
        val threeEnd = LocalDateTime.of(2023, 12, 21, 11, 50, 0)

        val scheduleDtoList = listOf(
            ScheduleDto(oneStart.toInstant().toEpochMilli(), oneEnd.toInstant().toEpochMilli()),
            ScheduleDto(twoStart.toInstant().toEpochMilli(), twoEnd.toInstant().toEpochMilli()),
            ScheduleDto(threeStart.toInstant().toEpochMilli(), threeEnd.toInstant().toEpochMilli()),
        )

        val expectedSchedule = listOf(
            Schedule(
                date = oneStart.toLocalDate(),
                hours = listOf(
                    HourRange(oneStart.toInstant(), oneEnd.toInstant()),
                    HourRange(twoStart.toInstant(), twoEnd.toInstant())
                )
                    .toImmutableList()
            ),
            Schedule(
                date = threeStart.toInstant().toLocalDate(),
                hours = listOf(
                    HourRange(threeStart.toInstant(), threeEnd.toInstant()),
                )
                    .toImmutableList()
            )
        )

        val scheduleList = scheduleDtoList.toSchedule()
        for (i in scheduleList.indices) {
            val a = scheduleList[i].date.compareTo(expectedSchedule[i].date)

            assertTrue(scheduleList[i].date.compareTo(expectedSchedule[i].date) == 0)

            for (j in scheduleList[i].hours.indices) {
                assertTrue(scheduleList[i].hours[j].start.toEpochMilli() == expectedSchedule[i].hours[j].start.toEpochMilli())
                assertTrue(scheduleList[i].hours[j].end.toEpochMilli() == expectedSchedule[i].hours[j].end.toEpochMilli())
            }
        }
    }
}