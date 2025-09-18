package com.alenniboris.personalmanager.domain.usecase.impl.activity

import com.alenniboris.personalmanager.domain.model.ActivityModelDomain
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.heart.HeartRateModelDomain
import com.alenniboris.personalmanager.domain.repository.IActivityRepository
import com.alenniboris.personalmanager.domain.repository.IHeartRepository
import com.alenniboris.personalmanager.domain.usecase.logic.activity.IAddActivityUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.heart.IAddHeartRateUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddActivityUseCaseImpl @Inject constructor(
    private val activityRepository: IActivityRepository,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val dispatchers: IAppDispatchers
) : IAddActivityUseCase {

    override suspend fun invoke(
        activity: ActivityModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext getCurrentUserUseCase.userFlow.value?.let { user ->
                activityRepository.addActivity(
                    activity = activity.copy(
                        userId = user.id
                    )
                )
            } ?: CustomResultModelDomain.Error(
                CommonExceptionModelDomain.ErrorGettingData
            )
        }
}