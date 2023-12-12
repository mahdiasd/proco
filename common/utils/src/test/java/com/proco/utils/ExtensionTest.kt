package com.proco.utils

import com.proco.extention.toHour
import com.proco.extention.toMinute
import org.junit.Assert
import org.junit.Test


class ExtensionTest {

    @Test
    fun toHour() {
        Assert.assertEquals("0".toHour() , "0")
        Assert.assertEquals("9".toHour() , "9")
        Assert.assertEquals("94".toHour() , "09")
        Assert.assertEquals("12".toHour() , "12")
        Assert.assertEquals("23".toHour() , "23")
        Assert.assertEquals("234".toHour() , "02")
    }
    @Test
    fun toMinute() {
        Assert.assertEquals("0".toMinute() , "0")
        Assert.assertEquals("9".toMinute() , "9")
        Assert.assertEquals("94".toMinute() , "09")
        Assert.assertEquals("12".toMinute() , "12")
        Assert.assertEquals("58".toMinute() , "58")
        Assert.assertEquals("60".toMinute() , "06")
        Assert.assertEquals("234".toMinute() , "02")
    }
}