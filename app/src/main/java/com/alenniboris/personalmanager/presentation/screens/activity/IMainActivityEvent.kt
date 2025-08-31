package com.alenniboris.personalmanager.presentation.screens.activity

sealed interface IMainActivityEvent {
    data object OpenSettings : IMainActivityEvent
}