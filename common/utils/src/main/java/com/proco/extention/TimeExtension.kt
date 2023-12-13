package com.proco.extention

import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

fun String?.toHour(completeLessTen: Boolean = false): String {
    if (this != null && completeLessTen && this.length == 1) return "0$this"
    if (this?.length == 2 && this.startsWith("0")) return this
    return this?.safeToInt()?.let {
        if (it > 24) "0${it.toString().firstOrNull()}"
        else it.toString()
    } ?: "00"
}

fun Int?.toHour(completeLessTen: Boolean = true): String {
    return if (this == null || this < 0) return "00"
    else this.toString().toHour(completeLessTen)
}

fun Int?.toMinute(completeLessTen: Boolean = true): String {
    return if (this == null || this < 0) return "00"
    else this.toString().toMinute(completeLessTen)
}

fun String?.toMinute(completeLessTen: Boolean = false): String {
    if (this != null && completeLessTen && this.length == 1) return "0$this"
    if (this?.length == 2 && this.startsWith("0")) return this
    return this?.safeToInt()?.let {
        if (it > 59) "0${it.toString().firstOrNull()}"
        else it.toString()
    } ?: "00"
}

fun Long.toInstant(): Instant {
    return Instant.ofEpochMilli(this)
}

fun LocalDateTime.toInstant(zoneId: ZoneId = ZoneId.systemDefault()): Instant {
    return this.atZone(zoneId).toInstant()
}

fun LocalTime.toInstant(localDate: LocalDateTime, zoneId: ZoneId = ZoneId.systemDefault()): Instant {
    val localDateTime = LocalDateTime.of(localDate.toLocalDate(), this)
    return localDateTime.toInstant(zoneId)
}

fun LocalTime.toInstant(instant: Instant, zoneId: ZoneId = ZoneId.systemDefault()): Instant {
    val l = LocalDateTime.of(instant.toLocalDateTime().toLocalDate(), this)
    return l.toInstant(zoneId)
}

fun Instant.toLocalDateTime(zoneId: ZoneId = ZoneId.systemDefault()): LocalDateTime {
    return LocalDateTime.ofInstant(this, zoneId)
}

fun Instant.toLocalTime(zoneId: ZoneId = ZoneId.systemDefault()): LocalTime {
    return this.atZone(zoneId).toLocalTime()
}
