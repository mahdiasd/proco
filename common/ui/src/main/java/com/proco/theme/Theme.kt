package com.proco.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

val shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(50.dp)

)


private val darkColors = darkColorScheme(
    primary = colorPrimaryDark,
    background = backgroundDark,
    secondary = secondaryDark,
    tertiary = itemDark,
    scrim = Color(0xFF797979),
    surface = white,
    error = red

)

private val lightColors = lightColorScheme(
    primary = colorPrimaryLight,
    background = backgroundLight,
    secondary = secondaryLight,
    tertiary = itemLight,
    scrim = Color(0xFFA7A5A5),
    surface = black,
    error = red
)

@Composable
fun ProcoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

//    val lightColors = CustomColors(colorPrimaryLight, secondaryLight, textColorLight, backgroundLight, green, red, true)
//    val darkColors = CustomColors(colorPrimaryDark, secondaryDark, textColorDark, backgroundDark, green, red, true)
//
    val colorScheme = when {
        darkTheme -> darkColors
        else -> lightColors
    }


    val view = LocalView.current

    if (!view.isInEditMode) {

    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        shapes = shapes,
        content = content
    )
//    EnableFullScreen()
}


@Composable
fun EnableFullScreen() {

    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = transparent.toArgb()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
    }
}

@Composable
fun DisableFullScreen(statusBarColor: Color = MaterialTheme.colorScheme.primary, navigationBarColor: Color = black) {
    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = statusBarColor.toArgb()
        window.navigationBarColor = navigationBarColor.toArgb()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
    }
}