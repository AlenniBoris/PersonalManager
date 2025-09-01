package com.alenniboris.personalmanager.data.repository

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.task.TaskModelDomain
import com.alenniboris.personalmanager.domain.repository.ITasksRepository

class TasksRepositoryImpl() : ITasksRepository {
    override suspend fun getAllTasks(
        userId: String
    ): CustomResultModelDomain<List<TaskModelDomain>, CommonExceptionModelDomain> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(updatedTask: TaskModelDomain): CustomResultModelDomain<Unit, CommonExceptionModelDomain> {
        TODO("Not yet implemented")
    }

    override suspend fun addTask(task: TaskModelDomain): CustomResultModelDomain<Unit, CommonExceptionModelDomain> {
        TODO("Not yet implemented")
    }

    override suspend fun removeTask(taskId: String): CustomResultModelDomain<Unit, CommonExceptionModelDomain> {
        TODO("Not yet implemented")
    }
}