package com.proco.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset

object DateUtils {

    fun add15Minutes(hour: Int, minute: Int): LocalTime {
        var updatedHour = hour
        var updatedMinute = minute + 15

        if (updatedMinute >= 60) {
            updatedHour += 1
            updatedMinute -= 60
        }

        if (updatedHour >= 24) {
            updatedHour = 0
        }

        return LocalTime.of(updatedHour, updatedMinute)
    }

    fun convertToInstant(dateMillis: Long, localTime: LocalTime): Instant {
        // Convert milliseconds since epoch to LocalDateTime
        val localDateTime = LocalDateTime.ofEpochSecond(dateMillis / 1000, 0, getLocalZoneOffset()).apply { withMinute(0) }

        // Combine LocalDateTime with LocalTime to get a full date-time representation
        val dateTimeWithTime = localDateTime.with(localTime)

        // Convert LocalDateTime to Instant using the system's default time zone
        return dateTimeWithTime.toInstant(getLocalZoneOffset())
    }

    fun getLocalZoneOffset(): ZoneOffset {
        return ZoneId
            .systemDefault()
            .rules
            .getOffset(
                Instant.now()
            )
    }

    fun isOverLapTime(start: LocalTime, end: LocalTime, selectedStart: LocalTime, selectedEnd: LocalTime): Boolean {
        return !(end.isBefore(selectedStart) || start.isAfter(selectedEnd))
    }
}