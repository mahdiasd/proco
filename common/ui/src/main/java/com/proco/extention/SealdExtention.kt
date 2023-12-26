package com.proco.extention

import com.proco.domain.model.user.Gender
import com.proco.ui.R

fun Gender.uiName(): Int {
    return when (this) {
        Gender.Female -> R.string.female
        Gender.Male -> R.string.male
        Gender.NonBinary -> R.string.non_binary
        Gender.Unknown -> R.string.unknown
    }
}