package com.alenniboris.personalmanager.presentation.screens.login_registration

import com.alenniboris.personalmanager.R

enum class LogRegScreenProcess {
    Login,
    Registration,
    PasswordReset
}

fun LogRegScreenProcess.toUiString() = when (this) {
    LogRegScreenProcess.Login -> R.string.login_process_text
    LogRegScreenProcess.Registration -> R.string.register_process_text
    LogRegScreenProcess.PasswordReset -> R.string.send_link_text
}

val listOfLogRegProcesses = listOf(
    LogRegScreenProcess.Login,
    LogRegScreenProcess.Registration
)