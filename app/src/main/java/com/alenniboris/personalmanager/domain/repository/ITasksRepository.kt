package com.alenniboris.personalmanager.domain.repository

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.task.TaskModelDomain

interface ITasksRepository {
    suspend fun getAllTasks(
        userId: String
    ): CustomResultModelDomain<List<TaskModelDomain>, CommonExceptionModelDomain>

    suspend fun updateTask(
        updatedTask: TaskModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>

    suspend fun addTask(
        task: TaskModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>

    suspend fun removeTask(
        taskId: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}