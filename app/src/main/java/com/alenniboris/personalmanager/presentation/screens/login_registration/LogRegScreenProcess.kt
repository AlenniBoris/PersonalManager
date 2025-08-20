package com.alenniboris.personalmanager.presentation.screens.login_registration

import com.alenniboris.personalmanager.R

enum class LogRegScreenProcess {
    Login,
    Registration
}

fun LogRegScreenProcess.toUiString() = when(this){
    LogRegScreenProcess.Login -> R.string.login_process_text
    LogRegScreenProcess.Registration -> R.string.register_process_text
}