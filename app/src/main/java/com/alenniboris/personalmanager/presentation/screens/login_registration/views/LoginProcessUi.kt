package com.alenniboris.personalmanager.presentation.screens.login_registration.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.screens.login_registration.ILogRegScreenIntent
import com.alenniboris.personalmanager.presentation.screens.login_registration.LogRegScreenState
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.emailIcon
import com.alenniboris.personalmanager.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.personalmanager.presentation.uikit.theme.logRegScreenBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.theme.logRegScreenBigTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.logRegScreenMainShape
import com.alenniboris.personalmanager.presentation.uikit.theme.passwordHidePicture
import com.alenniboris.personalmanager.presentation.uikit.theme.passwordShowPicture
import com.alenniboris.personalmanager.presentation.uikit.views.AppTextField

@Composable
fun LoginProcessUi(
    state: LogRegScreenState.Login,
    proceedIntent: (ILogRegScreenIntent) -> Unit
) {

    AppTextField(
        modifier = Modifier
            .padding(logRegScreenBigTopPadding)
            .background(
                color = enterTextFieldColor,
                shape = logRegScreenMainShape
            )
            .fillMaxWidth()
            .testTag(tag = "login_email"),
        value = state.email,
        onValueChanged = {
            proceedIntent(
                ILogRegScreenIntent.UpdateEmail(it)
            )
        },
        placeholder = stringResource(R.string.email_placeholder),
        icon = painterResource(emailIcon),
        iconTestTagPrefix = "login_email"
    )

    AppTextField(
        modifier = Modifier
            .padding(logRegScreenBigTopPadding)
            .background(
                color = enterTextFieldColor,
                shape = logRegScreenMainShape
            )
            .fillMaxWidth()
            .testTag(tag = "login_password"),
        value = state.password,
        onValueChanged = {
            proceedIntent(
                ILogRegScreenIntent.UpdatePassword(it)
            )
        },
        isPasswordField = true,
        isPasswordVisible = state.isPasswordVisible,
        placeholder = stringResource(R.string.password_placeholder),
        icon = painterResource(
            if (!state.isPasswordVisible) passwordHidePicture else passwordShowPicture
        ),
        onIconClicked = {
            proceedIntent(
                ILogRegScreenIntent.UpdatePasswordVisibility
            )
        },
        iconTestTagPrefix = "login_password"
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
                    .background(logRegScreenBackgroundColor),
                verticalArrangement = Arrangement.Center
            ) {
                LoginProcessUi(
                    state = LogRegScreenState.Login(),
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
                    .background(logRegScreenBackgroundColor),
                verticalArrangement = Arrangement.Center
            ) {
                LoginProcessUi(
                    state = LogRegScreenState.Login(),
                    proceedIntent = {}
                )
            }
        }
    }
}