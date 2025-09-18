package com.alenniboris.personalmanager.domain.usecase.impl.activity

import com.alenniboris.personalmanager.domain.model.ActivityModelDomain
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.repository.IActivityRepository
import com.alenniboris.personalmanager.domain.usecase.logic.activity.IGetActivitiesByDateUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class GetActivitiesByDateUseCaseImpl @Inject constructor(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val activityRepository: IActivityRepository,
    private val dispatchers: IAppDispatchers
) : IGetActivitiesByDateUseCase {

    override suspend fun invoke(
        date: Date
    ): CustomResultModelDomain<List<ActivityModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext getCurrentUserUseCase.userFlow.value?.let { user ->
                activityRepository.getActivitiesListByDate(
                    date = date,
                    userId = user.id
                )
            } ?: CustomResultModelDomain.Error(
                CommonExceptionModelDomain.UnknownException
            )
        }
}