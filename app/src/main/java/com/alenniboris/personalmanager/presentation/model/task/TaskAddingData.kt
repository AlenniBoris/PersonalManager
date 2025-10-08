package com.alenniboris.personalmanager.presentation.model.task

import com.alenniboris.personalmanager.domain.model.task.TaskModelDomain
import com.alenniboris.personalmanager.domain.model.task.TaskPriority
import com.alenniboris.personalmanager.domain.model.task.TaskStatus
import com.alenniboris.personalmanager.domain.utils.stripTime
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class TaskAddingData(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val dueDate: Date = Calendar.getInstance().time.stripTime(),
    val dueTime: Date = Calendar.getInstance().time.stripTime(),
    val createdDate: Date = Calendar.getInstance().time,
    val priority: TaskPriority = TaskPriority.Low,
    val status: TaskStatus = TaskStatus.Pending
) {
    val selectedDateText: String = SimpleDateFormat(
        "dd.MM.yyyy", Locale.getDefault()
    ).format(this.dueDate)
    val selectedTimeText: String = SimpleDateFormat(
        "HH:mm", Locale.getDefault()
    ).format(this.dueTime)

    fun toTaskDomainModel(): TaskModelDomain =
        TaskModelDomain(
            id = this.id,
            userId = this.userId,
            title = this.title,
            description = this.description,
            dueDate = this.dueDate,
            dueTime = this.dueTime,
            createdDate = this.createdDate,
            priority = this.priority,
            status = this.status
        )
}
