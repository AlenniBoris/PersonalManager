package com.alenniboris.personalmanager.presentation.screens.tasks

interface ITasksScreenEvent {
    data object OpenPersonalScreen: ITasksScreenEvent
    data class ShowToast(val messageId: Int) : ITasksScreenEvent
}