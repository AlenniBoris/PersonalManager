package com.alenniboris.personalmanager.presentation.screens.login_registration.views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.alenniboris.personalmanager.presentation.screens.login_registration.ILogRegScreenIntent
import com.alenniboris.personalmanager.presentation.screens.login_registration.LogRegScreenProcess
import com.alenniboris.personalmanager.presentation.screens.login_registration.LogRegScreenState
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify


class LogRegScreenUiTest {

    @get:Rule
    val composeRule = createComposeRule()
    private val proceedIntent: (ILogRegScreenIntent) -> Unit = mock()

    @Test
    fun login_elements_are_visible() {
        composeRule.setContent {
            LogRegScreenUi(
                state = LogRegScreenState.Login(),
                proceedIntent = proceedIntent,
                isTest = true
            )
        }
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("login_email").assertIsDisplayed()
        composeRule.onNodeWithTag("login_password").assertIsDisplayed()
        composeRule.onNodeWithTag("log_reg_final_button").assertIsDisplayed()
        composeRule.onNodeWithTag("login_reset_password_button").assertIsDisplayed()
    }

    @Test
    fun login_email_field_is_filling_correctly() {

        composeRule.setContent {
            var state by remember { mutableStateOf(LogRegScreenState.Login()) }

            LogRegScreenUi(
                state = state,
                proceedIntent = { intent ->
                    when (intent) {
                        is ILogRegScreenIntent.UpdateEmail -> {
                            state = state.copy(email = intent.newValue)
                        }

                        else -> {}
                    }
                },
                isTest = true
            )
        }
        composeRule.waitForIdle()
        val testString = "text"
        composeRule.onNodeWithTag("login_email").performTextInput(testString)
        composeRule.onNodeWithTag("login_email").assert(hasText(testString))
    }

    @Test
    fun login_password_field_is_filling_correctly() {

        composeRule.setContent {
            var state by remember { mutableStateOf(LogRegScreenState.Login()) }

            LogRegScreenUi(
                state = state,
                proceedIntent = { intent ->
                    when (intent) {
                        is ILogRegScreenIntent.UpdatePassword -> {
                            state = state.copy(password = intent.newValue)
                        }

                        else -> {}
                    }
                },
                isTest = true
            )
        }
        composeRule.waitForIdle()
        val testString = "text"
        composeRule.onNodeWithTag("login_password").performTextInput(testString)
        composeRule.onNodeWithTag("login_password").assert(hasText(testString))
    }

    @Test
    fun login_password_field_is_visibility_button_works_correctly() {

        composeRule.setContent {
            var state by remember { mutableStateOf(LogRegScreenState.Login()) }

            LogRegScreenUi(
                state = state,
                proceedIntent = { intent ->
                    when (intent) {
                        is ILogRegScreenIntent.UpdatePassword -> {
                            state = state.copy(password = intent.newValue)
                        }

                        is ILogRegScreenIntent.UpdatePasswordVisibility -> {
                            state = state.copy(isPasswordVisible = !state.isPasswordVisible)
                        }

                        else -> {}
                    }
                },
                isTest = true
            )
        }
        composeRule.waitForIdle()

        val testString = "text"

        composeRule.onNodeWithTag("login_password").performTextInput(testString)
        composeRule.onNodeWithTag("login_password").assertExists()
        composeRule.onNodeWithText(testString).assertExists()
        composeRule.onNodeWithTag("app_text_field_visibility_button_login_password").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText(testString).assertIsDisplayed()
    }

    @Test
    fun logreg_screen_final_button_action_performs() {

        composeRule.setContent {
            LogRegScreenUi(
                state = LogRegScreenState.Login(),
                proceedIntent = proceedIntent,
                isTest = true
            )
        }
        composeRule.waitForIdle()

        composeRule.onNodeWithTag("log_reg_final_button").performClick()
        verify(proceedIntent).invoke(ILogRegScreenIntent.ProceedFinalButtonAction)
    }

    @Test
    fun login_switch_to_registration_works() {
        composeRule.setContent {
            var state by remember { mutableStateOf<LogRegScreenState>(LogRegScreenState.Login()) }
            LogRegScreenUi(
                state = state,
                proceedIntent = { intent ->
                    when (intent) {
                        is ILogRegScreenIntent.ChangeProcess -> {
                            state = when (intent.process) {
                                LogRegScreenProcess.Login -> LogRegScreenState.Login()
                                LogRegScreenProcess.Registration -> LogRegScreenState.Registration()
                                LogRegScreenProcess.PasswordReset -> LogRegScreenState.PasswordReset()
                            }
                        }

                        else -> {}
                    }
                },
                isTest = true
            )
        }
        composeRule.waitForIdle()

        composeRule.onNodeWithTag("app_button_row_button_Register").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("registration_name").assertIsDisplayed()
        composeRule.onNodeWithTag("registration_email").assertIsDisplayed()
        composeRule.onNodeWithTag("registration_password").assertIsDisplayed()
        composeRule.onNodeWithTag("registration_password_check").assertIsDisplayed()
    }

    @Test
    fun register_passwords_visibility_buttons_work() {
        var state = LogRegScreenState.Registration()
        composeRule.setContent {
            LogRegScreenUi(
                state = state,
                proceedIntent = { intent ->
                    when (intent) {
                        is ILogRegScreenIntent.UpdatePassword -> {
                            state = state.copy(password = intent.newValue)
                        }

                        is ILogRegScreenIntent.UpdatePasswordCheck -> {
                            state = state.copy(passwordCheck = intent.newValue)
                        }

                        is ILogRegScreenIntent.UpdatePasswordVisibility -> {
                            state = state.copy(isPasswordVisible = !state.isPasswordVisible)
                        }

                        is ILogRegScreenIntent.UpdatePasswordCheckVisibility -> {
                            state =
                                state.copy(isPasswordCheckVisible = !state.isPasswordCheckVisible)
                        }

                        else -> {}
                    }
                },
                isTest = true
            )
        }
        composeRule.waitForIdle()

        val testString = "text"
        val testCheckString = "text check"

        composeRule.onNodeWithTag("registration_password").performTextInput(testString)
        composeRule.onNodeWithTag("registration_password_check").performTextInput(testCheckString)
        composeRule.waitForIdle()
        assert(!state.isPasswordVisible)
        assert(!state.isPasswordCheckVisible)

        composeRule.onNodeWithTag("app_text_field_visibility_button_registration_password")
            .performClick()
        composeRule.onNodeWithTag("app_text_field_visibility_button_registration_password_check")
            .performClick()
        composeRule.waitForIdle()
        assert(state.isPasswordVisible)
        assert(state.isPasswordCheckVisible)
    }

    @Test
    fun login_switch_to_password_reset_works() {
        composeRule.setContent {
            var state by remember { mutableStateOf<LogRegScreenState>(LogRegScreenState.Login()) }
            LogRegScreenUi(
                state = state,
                proceedIntent = { intent ->
                    when (intent) {
                        is ILogRegScreenIntent.ChangeProcess -> {
                            state = when (intent.process) {
                                LogRegScreenProcess.Login -> LogRegScreenState.Login()
                                LogRegScreenProcess.Registration -> LogRegScreenState.Registration()
                                LogRegScreenProcess.PasswordReset -> LogRegScreenState.PasswordReset()
                            }
                        }

                        else -> {}
                    }
                },
                isTest = true
            )
        }
        composeRule.waitForIdle()

        composeRule.onNodeWithTag("login_reset_password_button").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("password_reset_email").assertIsDisplayed()
        composeRule.onNodeWithTag("password_reset_back_button").assertIsDisplayed()
    }

    @Test
    fun password_reset_email_field_is_filling_correctly() {

        composeRule.setContent {
            var state by remember { mutableStateOf(LogRegScreenState.PasswordReset()) }

            LogRegScreenUi(
                state = state,
                proceedIntent = { intent ->
                    when (intent) {
                        is ILogRegScreenIntent.UpdateEmail -> {
                            state = state.copy(email = intent.newValue)
                        }

                        else -> {}
                    }
                },
                isTest = true
            )
        }
        composeRule.waitForIdle()
        val testString = "text"
        composeRule.onNodeWithTag("password_reset_email").performTextInput(testString)
        composeRule.onNodeWithTag("password_reset_email").assert(hasText(testString))
    }

    @Test
    fun password_reset_switch_to_login_works() {
        composeRule.setContent {
            var state by remember { mutableStateOf<LogRegScreenState>(LogRegScreenState.PasswordReset()) }
            LogRegScreenUi(
                state = state,
                proceedIntent = { intent ->
                    when (intent) {
                        is ILogRegScreenIntent.ChangeBackToLogin -> {
                            state = LogRegScreenState.Login()
                        }

                        else -> {}
                    }
                },
                isTest = true
            )
        }
        composeRule.waitForIdle()

        composeRule.onNodeWithTag("password_reset_back_button").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("login_email").assertIsDisplayed()
        composeRule.onNodeWithTag("login_password").assertIsDisplayed()
    }
}