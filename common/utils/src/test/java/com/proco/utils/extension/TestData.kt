package com.proco.utils.extension

import com.proco.extention.safeDecode
import com.proco.extention.safeEncode
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test

@Serializable
private data class TestData(val name: String, val age: Int)

class JsonExtensionTest {

    @Test
    fun `safeDecode should decode valid JSON string`() {
        val json = """{"name": "John", "age": 25}"""
        val decoded = Json.safeDecode<TestData>(json)
        Assert.assertEquals(TestData("John", 25), decoded)
    }

    @Test
    fun `safeDecode should handle null or empty JSON string and return null`() {
        val decodedNull = Json.safeDecode<TestData>(null)
        Assert.assertNull(decodedNull)

        val decodedEmpty = Json.safeDecode<TestData>("")
        Assert.assertNull(decodedEmpty)
    }

    @Test
    fun `safeDecode should handle invalid JSON string and return null`() {
        val invalidJson = "{invalid_json}"
        val decodedInvalid = Json.safeDecode<TestData>(invalidJson)
        Assert.assertNull(decodedInvalid)
    }

    @Test
    fun `safeEncode should encode object to JSON string`() {
        val testData = TestData("Alice", 30)
        val encoded = Json.decodeFromString<TestData>(testData.safeEncode())
        Assert.assertEquals(testData, encoded)
    }

    @Test
    fun `safeEncode should handle null object and return empty JSON string`() {
        val nullTestData: TestData? = null
        val encodedNull = Json.safeDecode<TestData>(nullTestData.safeEncode())
        Assert.assertNull(encodedNull)
    }
}
