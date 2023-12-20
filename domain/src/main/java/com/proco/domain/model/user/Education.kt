package com.proco.domain.model.user

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable


@Serializable
@Stable
sealed class Education {
    data object Diploma : Education() // دیپلم
    data object AssociateDegree : Education() // فوق دیپلم
    data object BachelorsDegree : Education() // کارشناسی
    data object MastersDegree : Education() // کارشناسی ارشد
    data object PhdDegree : Education() // دکتری
}