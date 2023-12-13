package com.proco.utils.extension

import com.proco.extention.toHour
import org.junit.Assert
import org.junit.Test

class ToHourTest {

    @Test
    fun `toHour should handle 1-digit input`() {
        Assert.assertEquals("0".toHour(), "0")
        Assert.assertEquals("9".toHour(), "9")
    }
    @Test
    fun `toHour should handle 1-digit input for complete less than ten`() {
        Assert.assertEquals("0".toHour(true), "00")
        Assert.assertEquals("9".toHour(true), "09")
    }
    @Test
    fun `toHour should handle 1-digit input for int`() {
        Assert.assertEquals(0.toHour(true), "00")
        Assert.assertEquals(9.toHour(true), "09")
        Assert.assertEquals(13.toHour(true), "13")
        Assert.assertEquals(14.toHour(true), "14")
    }

    @Test
    fun `toHour should handle 2-digit input`() {
        Assert.assertEquals("94".toHour(), "09")
        Assert.assertEquals("12".toHour(), "12")
        Assert.assertEquals("23".toHour(), "23")
    }

    @Test
    fun `toHour should handle 3-digit input`() {
        Assert.assertEquals("234".toHour(), "02")
    }
}
