package com.proco.app

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.proco.theme.ProcoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ProcoTheme {
                ChangeSystemBarsTheme(activity = this)
                AppNavigation()
            }
        }
    }
}

@Preview
@Composable
fun Test() {
    Text(text = "asdfasdfasdf")
}

@Composable
private fun ChangeSystemBarsTheme(activity: MainActivity) {
    val barColor = MaterialTheme.colorScheme.background.toArgb()
    val theme = isSystemInDarkTheme()

    LaunchedEffect(theme) {
        if (theme) {
            activity.enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.dark(barColor),
                navigationBarStyle = SystemBarStyle.dark(barColor),
            )
        } else {
            activity.enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(barColor, barColor),
                navigationBarStyle = SystemBarStyle.light(barColor, barColor),
            )
        }
    }
}

