package com.alenniboris.personalmanager.domain.usecase.impl.tasks

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.task.TaskModelDomain
import com.alenniboris.personalmanager.domain.repository.ITasksRepository
import com.alenniboris.personalmanager.domain.usecase.logic.tasks.IRemoveTaskUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoveTaskUseCaseImpl @Inject constructor(
    private val tasksRepository: ITasksRepository,
    private val dispatchers: IAppDispatchers
) : IRemoveTaskUseCase {
    override suspend fun invoke(
        task: TaskModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext tasksRepository.removeTask(
            taskId = task.id
        )
    }
}