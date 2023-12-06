package com.proco.schedule.utils

import com.proco.domain.model.schedule.Schedule
import com.proco.domain.model.time.HoursOfDay
import kotlinx.collections.immutable.ImmutableList

object ScheduleUtils {

    fun checkIsSchedule(schedule: ImmutableList<Schedule>, currentDay: Int, hoursOfDay: HoursOfDay): Boolean {
        if (schedule.size - 1 < currentDay) return false
        else {
            schedule[currentDay].hours.find { it.id == hoursOfDay.id }?.let { return true }
        }
        return false
    }
}