package com.alenniboris.personalmanager.presentation.mapper

import androidx.compose.ui.graphics.Color
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.task.TaskPriority
import com.alenniboris.personalmanager.domain.model.task.TaskStatus
import com.alenniboris.personalmanager.presentation.uikit.theme.taskHighPriorityColor
import com.alenniboris.personalmanager.presentation.uikit.theme.taskLowPriorityColor
import com.alenniboris.personalmanager.presentation.uikit.theme.taskMediumPriorityColor
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenCompletedTaskIconColor
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenPendingTaskIconColor
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenSkippedTaskIconColor

fun TaskStatus.toUiPicture(): Int = when (this) {
    TaskStatus.Skipped -> R.drawable.skipped_tasks_option
    TaskStatus.Pending -> R.drawable.pending_task
    TaskStatus.Completed -> R.drawable.completed_task
    TaskStatus.Undefined -> R.drawable.pending_task
}

fun TaskStatus.toUiColor(): Color = when (this) {
    TaskStatus.Skipped -> tasksScreenSkippedTaskIconColor
    TaskStatus.Pending -> tasksScreenPendingTaskIconColor
    TaskStatus.Completed -> tasksScreenCompletedTaskIconColor
    TaskStatus.Undefined -> tasksScreenPendingTaskIconColor
}

fun TaskStatus.toUiString(): Int = when (this) {
    TaskStatus.Skipped -> R.string.skipped_task_text
    TaskStatus.Pending -> R.string.pending_task_text
    TaskStatus.Completed -> R.string.completed_task_text
    TaskStatus.Undefined -> R.string.pending_task_text
}

fun TaskPriority.toUiString(): Int = when (this) {
    TaskPriority.Low -> R.string.task_low_priority_text
    TaskPriority.Medium -> R.string.task_medium_priority_text
    TaskPriority.High -> R.string.task_high_priority_text
    TaskPriority.Undefined -> R.string.task_medium_priority_text
}

fun TaskPriority.toUiColor(): Color = when (this) {
    TaskPriority.Low -> taskLowPriorityColor
    TaskPriority.Medium -> taskMediumPriorityColor
    TaskPriority.High -> taskHighPriorityColor
    TaskPriority.Undefined -> taskMediumPriorityColor
}