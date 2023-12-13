package com.proco.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.proco.base.BaseScreen
import com.proco.extention.animateClickable
import com.proco.extention.baseModifier
import com.proco.theme.ProcoTheme
import com.proco.ui.button.ProcoButton
import com.proco.ui.spacer.CustomSpacer
import com.proco.ui.text.BodyLargeText
import com.proco.ui.text.HeadLargeText
import com.proco.ui.text.TitleMediumText
import com.proco.ui.text_field.ProcoTextField


@Preview(showBackground = true)
@Composable
private fun Preview() {
    ProcoTheme {
        BaseScreen(modifier = Modifier.baseModifier()) {
            LoginScreenContent()
        }
    }
}

@Composable
fun LoginScreen(
    vm: LoginViewModel = hiltViewModel(),
    onRegister: () -> Unit
) {
    val uiState = vm.uiState.collectAsState().value
    BaseScreen(modifier = Modifier.baseModifier()) {
        LoginScreenContent(
            isLoading = uiState.isLoading,
            email = uiState.username,
            password = uiState.password,
            onEmailChanged = { vm.onUsernameChanged(it) },
            onPasswordChanged = { vm.onPasswordChanged(it) },
            onLogin = { vm.onTriggerEvent(LoginUiEvent.LoginClicked) },
            onRegister = onRegister
        )
    }

}

@Composable
private fun LoginScreenContent(
    isLoading: Boolean = false,
    email: String = "",
    password: String = "",
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onLogin: () -> Unit = {},
    onRegister: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = com.proco.ui.R.drawable.login_background),
            contentDescription = "login-vector",
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background)
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HeadLargeText(text = stringResource(id = com.proco.ui.R.string.login_here))

                TitleMediumText(text = stringResource(id = com.proco.ui.R.string.login_text), maxLines = 2)
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProcoTextField(modifier = Modifier.fillMaxWidth(), value = email, onValueChange = onEmailChanged, hint = stringResource(com.proco.ui.R.string.email))

                ProcoTextField(
                    modifier = Modifier.fillMaxWidth(), value = password, onValueChange = onPasswordChanged, hint = stringResource(com.proco.ui.R.string.password),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
                )

                CustomSpacer(48.dp)

                ProcoButton(modifier = Modifier.fillMaxWidth(), text = stringResource(id = com.proco.ui.R.string.login), isLoading = isLoading, onClick = onLogin)
            }

            BodyLargeText(
                modifier = Modifier.animateClickable { onRegister() },
                text = stringResource(id = com.proco.ui.R.string.create_new_account)
            )
        }
    }
}
