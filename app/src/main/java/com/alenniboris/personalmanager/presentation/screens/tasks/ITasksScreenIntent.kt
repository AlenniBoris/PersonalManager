package com.alenniboris.personalmanager.presentation.screens.tasks

import com.alenniboris.personalmanager.domain.model.task.TaskPriority
import com.alenniboris.personalmanager.domain.model.task.TaskStatus
import com.alenniboris.personalmanager.presentation.model.task.TaskModelUi
import java.util.Date

sealed interface ITasksScreenIntent {
    data class UpdateScreenOption(val option: TasksScreenOption) : ITasksScreenIntent
    data object UpdateAddTaskDialogVisibility : ITasksScreenIntent

    data class UpdateAddTaskTitle(val newValue: String) : ITasksScreenIntent
    data class UpdateAddTaskDescription(val newValue: String) : ITasksScreenIntent
    data class UpdateAddTaskPriority(val newValue: TaskPriority) : ITasksScreenIntent
    data class UpdateAddTaskDate(val newValue: Date) : ITasksScreenIntent
    data class UpdateAddTaskTime(val newValue: Date) : ITasksScreenIntent
    data object ProceedAddTaskAction : ITasksScreenIntent
    data class UpdateDatePickerVisibility(val isEditor: Boolean = false) : ITasksScreenIntent
    data class UpdateTimePickerVisibility(val isEditor: Boolean = false) : ITasksScreenIntent
    data class SelectEditedTask(val task: TaskModelUi?) : ITasksScreenIntent
    data class RemoveTask(val task: TaskModelUi) : ITasksScreenIntent
    data class UpdateEditedTaskTitle(val newValue: String) : ITasksScreenIntent
    data class UpdateEditedTaskDescription(val newValue: String) : ITasksScreenIntent
    data class UpdateEditedTaskDate(val newValue: Date) : ITasksScreenIntent
    data class UpdateEditedTaskTime(val newValue: Date) : ITasksScreenIntent
    data class UpdateEditedTaskPriority(val newValue: TaskPriority) : ITasksScreenIntent
    data class UpdateEditedTaskStatus(val newValue: TaskStatus) : ITasksScreenIntent
    data object ProceedUpdateTaskAction : ITasksScreenIntent
    data class UpdateSelectedTask(val task: TaskModelUi?) : ITasksScreenIntent
    data object UpdateDateFilterPickerVisibility : ITasksScreenIntent
    data class UpdateSelectedFilterDate(val date: Date?) : ITasksScreenIntent
}