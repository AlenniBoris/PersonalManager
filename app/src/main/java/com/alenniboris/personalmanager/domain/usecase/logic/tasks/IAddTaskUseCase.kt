package com.alenniboris.personalmanager.domain.usecase.logic.tasks

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.task.TaskModelDomain

interface IAddTaskUseCase {
    suspend fun invoke(
        task: TaskModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}