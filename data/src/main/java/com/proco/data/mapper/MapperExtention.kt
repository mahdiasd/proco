package com.proco.data.mapper

import com.proco.domain.model.schedule.HourRange
import com.proco.domain.model.schedule.Schedule
import com.proco.domain.model.schedule.ScheduleDto
import com.proco.extention.safeAdd
import com.proco.extention.toInstant
import com.proco.extention.toLocalDate
import kotlinx.collections.immutable.toImmutableList
import java.time.Instant

fun List<ScheduleDto>.toSchedule(): List<Schedule> {
    val scheduleList = mutableListOf<Schedule>()

    this.forEach { scheduleDto ->
        val date = Instant.ofEpochMilli(scheduleDto.start)
        val hourRange = HourRange(start = scheduleDto.start.toInstant(), scheduleDto.end.toInstant())

        scheduleList.find {
            it.date.compareTo(date.toLocalDate()) == 0
        }?.let { schedule: Schedule ->
            schedule.hours.toMutableList().safeAdd(hourRange)
        } ?: run {
            scheduleList.add(Schedule(date = date.toLocalDate(), hours = listOf(hourRange).toImmutableList()))
        }
    }
    return scheduleList
}