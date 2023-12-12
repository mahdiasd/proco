package com.proco.utils

import junit.framework.TestCase.assertEquals
import org.junit.Test
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
}



