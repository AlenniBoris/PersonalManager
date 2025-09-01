package com.alenniboris.personalmanager.domain.usecase.impl.tasks

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.task.TaskModelDomain
import com.alenniboris.personalmanager.domain.repository.ITasksRepository
import com.alenniboris.personalmanager.domain.usecase.logic.tasks.IAddTaskUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddTaskUseCaseImpl @Inject constructor(
    private val tasksRepository: ITasksRepository,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val dispatchers: IAppDispatchers
) : IAddTaskUseCase {

    override suspend fun invoke(
        task: TaskModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        return@withContext getCurrentUserUseCase.userFlow.value?.let {
            tasksRepository.addTask(task = task.copy(userId = it.id))
        } ?: CustomResultModelDomain.Error(
            CommonExceptionModelDomain.ErrorGettingData
        )
    }
}