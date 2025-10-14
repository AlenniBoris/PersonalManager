package com.alenniboris.personalmanager.presentation.screens.login_registration.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.screens.login_registration.ILogRegScreenIntent
import com.alenniboris.personalmanager.presentation.screens.login_registration.LogRegScreenState
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.emailIcon
import com.alenniboris.personalmanager.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.personalmanager.presentation.uikit.theme.logRegScreenBigTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.logRegScreenMainShape
import com.alenniboris.personalmanager.presentation.uikit.views.AppCustomButton
import com.alenniboris.personalmanager.presentation.uikit.views.AppTextField

@Composable
fun PasswordResetProcessUi(
    state: LogRegScreenState.PasswordReset,
    proceedIntent: (ILogRegScreenIntent) -> Unit
) {

    AppTextField(
        modifier = Modifier
            .background(
                color = enterTextFieldColor,
                shape = logRegScreenMainShape
            )
            .fillMaxWidth(),
        value = state.email,
        onValueChanged = {
            proceedIntent(
                ILogRegScreenIntent.UpdateEmail(it)
            )
        },
        placeholder = stringResource(R.string.email_placeholder),
        icon = painterResource(emailIcon)
    )

    AppCustomButton(
        modifier = Modifier
            .padding(logRegScreenBigTopPadding)
            .fillMaxWidth(),
        onClick = {
            proceedIntent(
                ILogRegScreenIntent.ChangeBackToLogin
            )
        },
        text = stringResource(R.string.go_back_to_login),
        icon = painterResource(R.drawable.back_icon)
    )
}

@Composable
@Preview
private fun LightTheme() {
    PersonalManagerTheme(
        darkTheme = false
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {
                PasswordResetProcessUi(
                    state = LogRegScreenState.PasswordReset(),
                    proceedIntent = {}
                )
            }
        }
    }
}

@Composable
@Preview
private fun DarkTheme() {
    PersonalManagerTheme(
        darkTheme = true
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {
                PasswordResetProcessUi(
                    state = LogRegScreenState.PasswordReset(),
                    proceedIntent = {}
                )
            }
        }
    }
}