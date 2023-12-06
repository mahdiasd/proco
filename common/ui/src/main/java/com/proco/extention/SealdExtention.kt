package com.proco.extention

import android.content.Context
import com.proco.domain.model.user.Gender
import com.proco.ui.R

fun Gender.name(context: Context): String {
    return when (this) {
        Gender.Female -> context.getString(R.string.female)
        Gender.Male -> context.getString(R.string.male)
        Gender.NonBinary -> context.getString(R.string.non_binary)
    }
}