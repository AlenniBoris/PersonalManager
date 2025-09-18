package com.alenniboris.personalmanager.domain.usecase.impl.activity

import com.alenniboris.personalmanager.domain.model.ActivityModelDomain
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.repository.IActivityRepository
import com.alenniboris.personalmanager.domain.usecase.logic.activity.IDeleteActivityUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteActivityUseCaseImpl @Inject constructor(
    private val activityRepository: IActivityRepository,
    private val dispatchers: IAppDispatchers
) : IDeleteActivityUseCase {

    override suspend fun invoke(
        activity: ActivityModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext activityRepository.deleteActivity(
                activity = activity
            )
        }
}