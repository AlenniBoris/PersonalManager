package com.alenniboris.personalmanager.domain.usecase.impl.heart

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.heart.HeartRateModelDomain
import com.alenniboris.personalmanager.domain.repository.IHeartRepository
import com.alenniboris.personalmanager.domain.usecase.logic.heart.IAddHeartRateUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddHeartRateUseCaseImpl @Inject constructor(
    private val heartRepository: IHeartRepository,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val dispatchers: IAppDispatchers
) : IAddHeartRateUseCase {

    override suspend fun invoke(
        heartRate: HeartRateModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext getCurrentUserUseCase.userFlow.value?.let { user ->
                heartRepository.addHeartRate(
                    heartRate = heartRate.copy(
                        userId = user.id
                    )
                )
            } ?: CustomResultModelDomain.Error(
                CommonExceptionModelDomain.ErrorGettingData
            )
        }
}