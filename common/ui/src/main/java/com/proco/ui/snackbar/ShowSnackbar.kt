package com.proco.ui.snackbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.proco.extention.screenSize
import com.proco.extention.withColor
import com.proco.theme.green
import com.proco.theme.red
import com.proco.theme.white
import kotlinx.coroutines.delay


sealed class SnackType {
    data object Idle : SnackType()
    data object StartShowCircle : SnackType()
    data object EndShowCircle : SnackType()
    data object IsExpanded : SnackType()
    data object Hide : SnackType()
}


@Composable
fun SuccessSnackBar(messageInt: Int, hasNotch: Boolean = com.proco.extention.hasNotch(), duration: Long = 6000L, finished: (() -> Unit)? = null) {
    ShowSnackBar(message = stringResource(id = messageInt), color = green, duration = duration, hasNotch = hasNotch, finished = finished)
}

@Composable
fun SuccessSnackBar(messageText: String?, hasNotch: Boolean = com.proco.extention.hasNotch(), duration: Long = 6000L, finished: (() -> Unit)? = null) {
    if (messageText.isNullOrEmpty()) return
    ShowSnackBar(message = messageText, duration = duration, color = green, hasNotch = hasNotch, finished = finished)
}

@Composable
fun FailedSnackBar(messageText: String?, hasNotch: Boolean = com.proco.extention.hasNotch(), duration: Long = 6000L, finished: (() -> Unit)? = null) {
    if (messageText.isNullOrEmpty()) return
    ShowSnackBar(message = messageText, duration = duration, hasNotch = hasNotch, color = red, finished = finished)
}

@Composable
fun FailedSnackBar(messageInt: Int, hasNotch: Boolean = com.proco.extention.hasNotch(), duration: Long = 6000L, finished: (() -> Unit)? = null) {
    ShowSnackBar(message = stringResource(id = messageInt), duration = duration, hasNotch = hasNotch, finished = finished)
}


@Composable
private fun ShowSnackBar(
    message: String,
    hasNotch: Boolean = com.proco.extention.hasNotch(),
    duration: Long = 6000L,
    color: Color = green,
    finished: (() -> Unit)? = null,
) {

    val type: MutableState<SnackType> = remember { mutableStateOf(SnackType.Idle) }
    val snackbarContentHeight = remember { mutableStateOf(0.dp) }

    val snackbarWidth: Dp by animateDpAsState(
        if (type.value is SnackType.IsExpanded) (screenSize().screenWidthDp - 0).dp else 45.dp,
        animationSpec = spring(stiffness = Spring.StiffnessLow, dampingRatio = 0.85f),
        label = "",
    )

    val snackbarHeight: Dp by animateDpAsState(
        if (type.value is SnackType.IsExpanded) (snackbarContentHeight.value).coerceAtLeast(80.dp) else 45.dp,
        animationSpec = spring(stiffness = Spring.StiffnessLow, dampingRatio = 0.85f),
        label = "",
    )

    val offsetY by animateDpAsState(
        if (snackbarHeight > 80.dp && hasNotch) 15.dp else 0.dp,
        label = ""
    )

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = type.value !is SnackType.Hide
        ) {
            Snackbar(
                containerColor = color,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .offset(y = offsetY)
                    .width(snackbarWidth)
                    .height(snackbarHeight)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .align(Alignment.CenterHorizontally),
                content = {
                    Text(
                        text = message,
                        modifier = Modifier.fillMaxWidth(1f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium.withColor(white),
                        onTextLayout = { textLayoutResult: TextLayoutResult ->
                            snackbarContentHeight.value = ((80.dp.value.toInt() + (textLayoutResult.lineCount.coerceAtMost(4) * (20.dp).value).toInt())).dp
                        }
                    )
                }
            )
        }
    }

    LaunchedEffect(key1 = type.value, block = {
        when (type.value) {
            is SnackType.Hide -> {
                finished?.invoke()
            }

            is SnackType.Idle -> {
                type.value = SnackType.StartShowCircle
            }

            is SnackType.IsExpanded -> {
                delay(duration)
                type.value = SnackType.EndShowCircle
            }

            is SnackType.StartShowCircle -> {
                delay(100L)
                type.value = SnackType.IsExpanded
            }

            is SnackType.EndShowCircle -> {
                delay(1500L)
                type.value = SnackType.Hide
            }
        }

    })

}
