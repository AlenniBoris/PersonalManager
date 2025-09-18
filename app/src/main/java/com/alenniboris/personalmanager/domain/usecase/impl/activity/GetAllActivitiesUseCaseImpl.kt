package com.alenniboris.personalmanager.domain.usecase.impl.activity

import com.alenniboris.personalmanager.domain.model.ActivityModelDomain
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.repository.IActivityRepository
import com.alenniboris.personalmanager.domain.usecase.logic.activity.IGetAllActivitiesUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllActivitiesUseCaseImpl @Inject constructor(
    private val activityRepository: IActivityRepository,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val dispatchers: IAppDispatchers
) : IGetAllActivitiesUseCase {

    override suspend fun invoke(): CustomResultModelDomain<List<ActivityModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext getCurrentUserUseCase.userFlow.value?.let { user ->
                activityRepository.getAllActivities(
                    userId = user.id
                )
            } ?: CustomResultModelDomain.Error(
                CommonExceptionModelDomain.ErrorGettingData
            )
        }
}