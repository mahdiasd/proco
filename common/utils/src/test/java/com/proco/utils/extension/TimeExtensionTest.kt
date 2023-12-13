package com.proco.utils.extension

import com.proco.extention.toInstant
import com.proco.extention.toLocalDateTime
import junit.framework.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TimeExtensionTest {

    @Test
    fun `no null values`() {
        val time = LocalTime.of(11, 57)
        val instant = time.toInstant(LocalDateTime.of(LocalDate.now(), time))
        val a = instant.toLocalDateTime().hour
        val b = instant.toLocalDateTime().minute
        assertTrue(instant.toLocalDateTime().hour == time.hour)
        assertTrue(instant.toLocalDateTime().minute == time.minute)
    }

}
