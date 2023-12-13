package com.proco.data.mapper

import com.proco.domain.model.schedule.Schedule
import com.proco.domain.model.schedule.ScheduleDto

class ScheduleMapper : Mapper<List<Schedule>, List<ScheduleDto>> {
    override fun mapFrom(from: List<Schedule>): List<ScheduleDto> {
        return from.flatMap { schedule ->
            schedule.hours.map {
                ScheduleDto(start = it.start.epochSecond, end = it.end.epochSecond)
            }
        }
    }


}