package com.alenniboris.personalmanager.presentation.screens.tasks

interface ITasksScreenEvent {
    data class ShowToast(val messageId: Int) : ITasksScreenEvent
}