package com.proco.ui.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.proco.theme.ProcoTheme
import com.proco.ui.R
import com.proco.ui.spacer.CustomSpacer
import com.proco.ui.text.BodyLargeText

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ProcoTheme {
        ErrorScreen(modifier = Modifier.fillMaxSize(), "Error happened")
    }
}

@Composable
fun ErrorScreen(modifier: Modifier, errorMessage: String) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            painter = painterResource(id = R.drawable.error_vector), contentDescription = "error"
        )

        CustomSpacer()

        BodyLargeText(text = errorMessage)
    }
}