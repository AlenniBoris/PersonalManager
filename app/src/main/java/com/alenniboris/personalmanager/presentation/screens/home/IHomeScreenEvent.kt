package com.alenniboris.personalmanager.presentation.screens.home

sealed interface IHomeScreenEvent {
    data object OpenPersonalScreen : IHomeScreenEvent
    data class ShowToast(val messageId: Int) : IHomeScreenEvent
}