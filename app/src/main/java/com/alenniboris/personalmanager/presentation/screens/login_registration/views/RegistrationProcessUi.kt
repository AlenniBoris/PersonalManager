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
import com.alenniboris.personalmanager.presentation.uikit.theme.personIcon
import com.alenniboris.personalmanager.presentation.uikit.views.AppTextField

@Composable
fun RegistrationProcessUi(
    state: LogRegScreenState.Registration,
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
            .testTag(tag = "registration_name"),
        value = state.user.name,
        onValueChanged = {
            proceedIntent(
                ILogRegScreenIntent.UpdateFullName(it)
            )
        },
        placeholder = stringResource(R.string.name_placeholder),
        icon = painterResource(personIcon)
    )

    AppTextField(
        modifier = Modifier
            .padding(logRegScreenBigTopPadding)
            .background(
                color = enterTextFieldColor,
                shape = logRegScreenMainShape
            )
            .fillMaxWidth()
            .testTag(tag = "registration_email"),
        value = state.user.email,
        onValueChanged = {
            proceedIntent(
                ILogRegScreenIntent.UpdateEmail(it)
            )
        },
        placeholder = stringResource(R.string.email_placeholder),
        icon = painterResource(emailIcon)
    )

    AppTextField(
        modifier = Modifier
            .padding(logRegScreenBigTopPadding)
            .background(
                color = enterTextFieldColor,
                shape = logRegScreenMainShape
            )
            .fillMaxWidth()
            .testTag(tag = "registration_password"),
        value = state.password,
        onValueChanged = {
            proceedIntent(
                ILogRegScreenIntent.UpdatePassword(it)
            )
        },
        placeholder = stringResource(R.string.password_placeholder),
        isPasswordField = true,
        isPasswordVisible = state.isPasswordVisible,
        icon = painterResource(
            if (!state.isPasswordVisible) passwordHidePicture else passwordShowPicture
        ),
        onIconClicked = {
            proceedIntent(
                ILogRegScreenIntent.UpdatePasswordVisibility
            )
        },
        iconTestTagPrefix = "registration_password"
    )

    AppTextField(
        modifier = Modifier
            .padding(logRegScreenBigTopPadding)
            .background(
                color = enterTextFieldColor,
                shape = logRegScreenMainShape
            )
            .fillMaxWidth()
            .testTag(tag = "registration_password_check"),
        value = state.passwordCheck,
        onValueChanged = {
            proceedIntent(
                ILogRegScreenIntent.UpdatePasswordCheck(it)
            )
        },
        isPasswordField = true,
        isPasswordVisible = state.isPasswordCheckVisible,
        placeholder = stringResource(R.string.password_check_placeholder),
        icon = painterResource(
            if (!state.isPasswordCheckVisible) passwordHidePicture else passwordShowPicture
        ),
        onIconClicked = {
            proceedIntent(
                ILogRegScreenIntent.UpdatePasswordCheckVisibility
            )
        },
        iconTestTagPrefix = "registration_password_check"
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
                RegistrationProcessUi(
                    state = LogRegScreenState.Registration(),
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
                RegistrationProcessUi(
                    state = LogRegScreenState.Registration(),
                    proceedIntent = {}
                )
            }
        }
    }
}