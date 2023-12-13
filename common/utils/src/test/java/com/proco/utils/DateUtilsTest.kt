package com.proco.utils

import com.proco.utils.DateUtils.convertToInstant
import com.proco.utils.DateUtils.getLocalZoneOffset
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime
import java.time.LocalTime

class DateUtilsTest {

    @Test
    fun add15Minutes() {
        var result = DateUtils.add15Minutes(12, 30)
        assertEquals(LocalTime.of(12, 45), result)

        // Test case 2: Add 15 minutes and cross the hour boundary
        result = DateUtils.add15Minutes(23, 45)
        assertEquals(LocalTime.of(0, 0), result)

        // Test case 3: Add 15 minutes to reach the next hour
        result = DateUtils.add15Minutes(11, 45)
        assertEquals(LocalTime.of(12, 0), result)

        // Test case 4: Add 15 minutes to reach the next day
        result = DateUtils.add15Minutes(23, 55)
        assertEquals(LocalTime.of(0, 10), result)

        // Test case 5: Add 15 minutes to midnight
        result = DateUtils.add15Minutes(0, 0)
        assertEquals(LocalTime.of(0, 15), result)
    }

    @Test
    fun testConvertToInstant() {
        // Given
        val dateMillis = 1702366440000 // Example milliseconds (Tue Dec 12 2023 11:04:00 local)
        val localTime = LocalTime.of(11, 4, 0)

        // When
        val resultInstant = convertToInstant(dateMillis, localTime)

        // Then
        val localDateTime = LocalDateTime.of(2023, 12, 12, 11, 4, 0)
        val expectedInstant = localDateTime.toInstant(getLocalZoneOffset())
        assertEquals(expectedInstant.toEpochMilli(), resultInstant.toEpochMilli())
    }


    @Test
    fun isOverLapTest() {
        assertTrue(
            DateUtils.isOverLapTime(
                start = LocalTime.of(8, 10),
                end = LocalTime.of(8, 15),
                selectedStart = LocalTime.of(8, 10),
                selectedEnd = LocalTime.of(8, 15)
            )
        )
        assertTrue(
            DateUtils.isOverLapTime(
                start = LocalTime.of(8, 10),
                end = LocalTime.of(8, 15),
                selectedStart = LocalTime.of(8, 11),
                selectedEnd = LocalTime.of(8, 40)
            )
        )
        assertTrue(
            DateUtils.isOverLapTime(
                start = LocalTime.of(8, 10),
                end = LocalTime.of(8, 15),
                selectedStart = LocalTime.of(8, 14),
                selectedEnd = LocalTime.of(8, 40)
            )
        )

        assertTrue(
            DateUtils.isOverLapTime(
                start = LocalTime.of(8, 10),
                end = LocalTime.of(8, 15),
                selectedStart = LocalTime.of(8, 9),
                selectedEnd = LocalTime.of(8, 10)
            )
        )

        assertFalse(
            DateUtils.isOverLapTime(
                start = LocalTime.of(8, 10),
                end = LocalTime.of(8, 15),
                selectedStart = LocalTime.of(8, 0),
                selectedEnd = LocalTime.of(8, 9)
            )
        )

        assertFalse(
            DateUtils.isOverLapTime(
                start = LocalTime.of(8, 10),
                end = LocalTime.of(8, 15),
                selectedStart = LocalTime.of(7, 10),
                selectedEnd = LocalTime.of(7, 15)
            )
        )
        assertFalse(
            DateUtils.isOverLapTime(
                start = LocalTime.of(0, 15),
                end = LocalTime.of(0, 44),
                selectedStart = LocalTime.of(23, 59),
                selectedEnd = LocalTime.of(0, 14)
            )
        )
    }

}



