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
    return decodeFromString<T>(json)
}

inline fun <reified T> T.safeEncode(): String {
    return Json.encodeToString(this)
}

