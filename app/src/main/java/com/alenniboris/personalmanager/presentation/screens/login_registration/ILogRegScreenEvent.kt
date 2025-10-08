package com.alenniboris.personalmanager.presentation.screens.login_registration

sealed interface ILogRegScreenEvent {

    data class ShowToast(val messageId: Int): ILogRegScreenEvent
    data object OpenSettings : ILogRegScreenEvent
}