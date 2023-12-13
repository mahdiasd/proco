package com.proco.utils.extension

import com.proco.extention.isNull
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class IsNullTest {

    @Test
    fun `test isNull with null value`() {
        val value: Any? = null
        assertTrue(value.isNull())
    }

    @Test
    fun `test isNull with non-null value`() {
        val value: Any? = "Hello"
        assertFalse(value.isNull())
    }

    @Test
    fun `test isNull with integer`() {
        val value: Any? = 42
        assertFalse(value.isNull())
    }

    @Test
    fun `test isNull with boolean`() {
        val value: Any? = true
        assertFalse(value.isNull())
    }

    @Test
    fun `test isNull with custom object`() {
        val value: Any? = CustomObject()
        assertFalse(value.isNull())
    }

    private class CustomObject

}
