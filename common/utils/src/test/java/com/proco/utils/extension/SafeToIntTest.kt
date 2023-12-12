package com.proco.utils.extension

import com.proco.extention.safeToInt
import org.junit.Assert
import org.junit.Test

class SafeToIntTest {

    @Test
    fun `safeToInt should convert valid string to int`() {
        Assert.assertEquals("123".safeToInt(), 123)
        Assert.assertEquals("0".safeToInt(), 0)
        Assert.assertEquals("-456".safeToInt(), -456)
    }

    @Test
    fun `safeToInt should handle invalid string and return default value`() {
        Assert.assertEquals("abc".safeToInt(), 0)
        Assert.assertEquals("12abc".safeToInt(), 0)
    }

    @Test
    fun `safeToInt should handle null string and return default value`() {
        Assert.assertEquals((null as String?).safeToInt(), 0)
    }

    @Test
    fun `safeToInt should handle null string and return specified default value`() {
        Assert.assertEquals((null as String?).safeToInt(defaultValue = 42), 42)
    }
}
