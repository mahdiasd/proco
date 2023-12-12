package com.proco.utils.extension

import com.proco.extention.anyNull
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

class AnyNullTest {

    @Test
    fun `no null values`() {
        assertFalse(anyNull(1, "Hello", 3.14, true))
    }

    @Test
    fun `one null value`() {
        assertTrue(anyNull(1, "Hello", null, 3.14, true))
    }

    @Test
    fun `multiple null values`() {
        assertTrue(anyNull(null, "Hello", null, 3.14, true))
    }

    @Test
    fun `all null values`() {
        assertTrue(anyNull(null, null, null))
    }

    @Test
    fun `empty input`() {
        assertFalse(anyNull())
    }

    @Test
    fun `mixed values with no null`() {
        assertFalse(anyNull(1, "Hello", 3.14, true, 'a'))
    }
}
