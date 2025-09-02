package com.alenniboris.personalmanager.presentation.screens.tasks

import com.alenniboris.personalmanager.domain.model.task.TaskPriority
import com.alenniboris.personalmanager.domain.model.task.TaskStatus

object TasksScreenValues {

    val TaskPriorityUiList: List<TaskPriority> =
        TaskPriority.entries.filter { it != TaskPriority.Undefined }

    val TaskStatusUiList: List<TaskStatus> =
        TaskStatus.entries.filter { it != TaskStatus.Undefined }
}