package com.alenniboris.personalmanager.domain.model.task

import java.util.Date

data class TaskModelDomain(
    val id: String,
    val userId: String,
    val title: String,
    val description: String,
    val dueDate: Date,
    val dueTime: Date,
    val priority: TaskPriority,
    val taskStatus: TaskStatus
)