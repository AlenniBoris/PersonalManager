package com.alenniboris.personalmanager.presentation.screens.health_screen

sealed interface IHealthScreenEvent {
    data class ShowToast(val messageId: Int) : IHealthScreenEvent
}