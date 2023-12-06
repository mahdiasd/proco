package com.proco.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp
import com.proco.ui.R

val customFontFamily = FontFamily(
    Font(R.font.sanfransico, FontWeight.Normal),
    Font(R.font.sanfransico, FontWeight.Bold)
)


val typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.manrope_bold, FontWeight.Bold)),
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 42.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.manrope, FontWeight.SemiBold)),
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.manrope_bold, FontWeight.Bold)),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.manrope_bold, FontWeight.Bold)),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.manrope, FontWeight.Medium)),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.manrope, FontWeight.SemiBold)),
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.manrope_bold, FontWeight.Bold)),
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)


/*------------------- Normal Styles -------------------------------*/
val NormalStyle =
    TextStyle(
        fontFamily = FontFamily(Font(R.font.manrope, FontWeight.Normal)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = bodyColor,
    )

val NormalStyleRtl =
    TextStyle(
        fontFamily = FontFamily(Font(R.font.iran_sans, FontWeight.Normal)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = bodyColor,
        textDirection = TextDirection.Rtl
    )