package com.alenniboris.personalmanager.domain.usecase.logic.tasks

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.task.TaskModelDomain

interface IGetAllTasksUseCase {
    suspend fun invoke(): CustomResultModelDomain<List<TaskModelDomain>, CommonExceptionModelDomain>
}