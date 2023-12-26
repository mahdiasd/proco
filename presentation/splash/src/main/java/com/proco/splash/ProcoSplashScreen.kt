package com.proco.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.ir.splash.SplashViewModel
import app.ir.splash.UpdateState
import com.proco.base.BaseScreen
import com.proco.extention.baseModifier
import com.proco.theme.ProcoTheme
import com.proco.theme.white
import com.proco.ui.R
import com.proco.ui.loading.LoadingScreen
import com.proco.ui.text.BodyMediumText
import com.proco.ui.text.TitleLargeText


@Preview(showSystemUi = true)
@Composable
private fun Preview(isLoading: Boolean = false) {
    ProcoTheme {
        BaseScreen(
            modifier = Modifier.baseModifier(),
            uiMessage = null
        ) {
            SplashScreenContent(false)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoadingPreview() = Preview(isLoading = true)

@Composable
fun ProcoSplashScreen(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    vm: SplashViewModel = hiltViewModel()
) {
    val uiState = vm.uiState.collectAsState().value
    BaseScreen(modifier = Modifier.fillMaxSize(), uiMessage = uiState.uiMessage) {
        SplashScreenContent(uiState.isLoading)
    }

    when (uiState.updateState) {
        UpdateState.Idle -> {}
        UpdateState.NoUpdate -> {
            if (uiState.isLoggedIn) navigateToHome()
            else navigateToLogin()
        }

        UpdateState.Suggest -> {}
        UpdateState.Force -> {}
        else -> {}
    }
}

@Composable
private fun SplashScreenContent(isLoading: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .safeContentPadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        TitleLargeText(text = stringResource(R.string.app_name), color = white)

        if (isLoading) {
            LoadingScreen(color = white)
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BodyMediumText(text = stringResource(R.string.failed_splash), color = white)
                BodyMediumText(text = stringResource(R.string.try_again), color = white)
            }
        }
    }
}