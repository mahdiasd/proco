package com.proco.extention

import android.util.Log
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Any?.dLog(plusTag: String = "", tag: String = "MyLog") {
    Log.d(tag, plusTag + " " + toString())
}

fun Any?.eLog(plusTag: String = "", tag: String = "MyLog") {
    Log.e(tag, plusTag + " " + toString())
}

fun anyNull(vararg items: Any?): Boolean {
    return items.any { it == null }
}

fun Any?.isNull(): Boolean {
    return this == null
}

inline fun <reified T> Json.safeDecode(json: String?): T? {
    if (json.isNullOrEmpty()) return null
    return try {
        decodeFromString<T>(json)
    } catch (e: Exception) {
        null
    }
}

inline fun <reified T> T.safeEncode(): String {
    return Json.encodeToString(this)
}

fun String?.safeToInt(defaultValue: Int = 0): Int =
    this?.run {
        try {
            toInt()
        } catch (e: NumberFormatException) {
            defaultValue
        }
    } ?: defaultValue

inline fun <reified T> List<T>.findIndex(predicate: (T) -> Boolean): Int? {
    for ((index, element) in withIndex()) {
        if (predicate(element)) {
            return index
        }
    }
    return null
}

fun <T> MutableList<T>?.safeAdd(element: T): MutableList<T> {
    if (this == null) {
        return mutableListOf(element)
    }
    if (element != null) {
        this.add(element)
    }
    return this
}
