package com.proco.utils.extension

import com.proco.extention.toMinute
import org.junit.Assert
import org.junit.Test

class ToMinuteTest {

    @Test
    fun `toMinute should handle 1-digit input`() {
        Assert.assertEquals("0".toMinute(), "0")
        Assert.assertEquals("9".toMinute(), "9")
    }

    @Test
    fun `toMinute should handle 2-digit input`() {
        Assert.assertEquals("94".toMinute(), "09")
        Assert.assertEquals("12".toMinute(), "12")
        Assert.assertEquals("58".toMinute(), "58")
    }

    @Test
    fun `toMinute should handle 2-digit input and convert 60 to 6`() {
        Assert.assertEquals("60".toMinute(), "06")
    }

    @Test
    fun `toMinute should handle 3-digit input`() {
        Assert.assertEquals("234".toMinute(), "02")
    }
}
