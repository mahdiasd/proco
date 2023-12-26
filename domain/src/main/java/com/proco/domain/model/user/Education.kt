package com.proco.domain.model.user

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable


@Serializable
@Stable
sealed class Education {
    data object Diploma : Education() // دیپلم
    data object Associate : Education() // فوق دیپلم
    data object Bachelors : Education() // کارشناسی
    data object Masters : Education() // کارشناسی ارشد
    data object Phd : Education() // دکتری
}