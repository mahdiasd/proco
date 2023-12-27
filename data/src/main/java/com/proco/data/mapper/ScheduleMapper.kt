package com.proco.data.mapper

import com.proco.domain.model.schedule.Schedule
import com.proco.domain.model.schedule.ScheduleDto
import javax.inject.Inject

class ScheduleMapper @Inject constructor() : Mapper<List<Schedule>, List<ScheduleDto>> {
    override fun mapFrom(from: List<Schedule>): List<ScheduleDto> {
        return from.flatMap { schedule ->
            schedule.hours.map {
                ScheduleDto(start = it.start.toString(), end = it.end.toString())
            }
        }
    }


}