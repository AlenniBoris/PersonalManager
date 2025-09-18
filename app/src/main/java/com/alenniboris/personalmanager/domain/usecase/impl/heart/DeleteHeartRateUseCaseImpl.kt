package com.alenniboris.personalmanager.domain.usecase.impl.heart

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.heart.HeartRateModelDomain
import com.alenniboris.personalmanager.domain.repository.IHeartRepository
import com.alenniboris.personalmanager.domain.usecase.logic.heart.IDeleteHeartRateUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteHeartRateUseCaseImpl @Inject constructor(
    private val heartRepository: IHeartRepository,
    private val dispatchers: IAppDispatchers
) : IDeleteHeartRateUseCase {

    override suspend fun invoke(
        heartRate: HeartRateModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext heartRepository.deleteHeartRate(
                heartRate = heartRate
            )
        }
}