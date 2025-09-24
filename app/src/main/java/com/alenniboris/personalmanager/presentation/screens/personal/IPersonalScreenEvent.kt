package com.alenniboris.personalmanager.presentation.screens.personal

sealed interface IPersonalScreenEvent {
    data class ShowToast(val messageId: Int) : IPersonalScreenEvent
    data object NavigateBack : IPersonalScreenEvent
}