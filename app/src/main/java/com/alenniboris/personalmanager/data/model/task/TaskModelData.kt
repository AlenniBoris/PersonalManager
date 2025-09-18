package com.alenniboris.personalmanager.data.model.task

import com.alenniboris.personalmanager.domain.model.task.TaskModelDomain
import com.alenniboris.personalmanager.domain.model.task.TaskPriority
import com.alenniboris.personalmanager.domain.model.task.TaskStatus
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import java.util.Date
import kotlin.text.toLong

data class TaskModelData(
    val id: String? = null,
    val userId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val dueDate: String? = null,
    val dueTime: String? = null,
    val createdDate: String? = null,
    val priority: String? = null,
    val status: String? = null
) {

    fun toUpdatesMap(): Map<String, String?> =
        mapOf(
            "id" to id,
            "userId" to userId,
            "title" to title,
            "description" to description,
            "dueDate" to dueDate,
            "dueTime" to dueTime,
            "createdDate" to createdDate,
            "priority" to priority,
            "status" to status
        )
}

fun TaskModelData.toModelDomain(): TaskModelDomain? =
    runCatching {

        TaskModelDomain(
            id = this.id!!,
            userId = this.userId!!,
            title = this.title!!,
            description = this.description!!,
            dueDate = Date(this.dueDate?.toLong()!!),
            dueTime = Date(this.dueTime?.toLong()!!),
            createdDate = Date(this.createdDate?.toLong()!!),
            priority = when (this.priority!!) {
                "Low" -> TaskPriority.Low
                "Medium" -> TaskPriority.Medium
                "High" -> TaskPriority.High
                else -> TaskPriority.Undefined
            },
            status = when (this.status!!) {
                "Completed" -> TaskStatus.Completed
                "Pending" -> TaskStatus.Pending
                "Skipped" -> TaskStatus.Skipped
                else -> TaskStatus.Undefined
            }
        )
    }.getOrElse { exception ->
        LogPrinter.printLog(
            tag = "!!!",
            message = """
                TaskModelData.toModelDomain(),
                
                ${exception.stackTraceToString()}
            """.trimIndent()
        )
        null
    }

fun TaskModelDomain.toModelData(): TaskModelData =
    TaskModelData(
        id = this.id,
        userId = this.userId,
        title = this.title,
        description = this.description,
        dueDate = this.dueDate.time.toString(),
        dueTime = this.dueTime.time.toString(),
        createdDate = this.createdDate.time.toString(),
        priority = this.priority.name,
        status = this.status.name
    )