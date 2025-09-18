package com.alenniboris.personalmanager.domain.usecase.impl.weight

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import com.alenniboris.personalmanager.domain.repository.IWeightRepository
import com.alenniboris.personalmanager.domain.usecase.logic.weight.IDeleteWeightUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteWeightUseCaseImpl @Inject constructor(
    private val weightRepository: IWeightRepository,
    private val dispatchers: IAppDispatchers
) : IDeleteWeightUseCase {

    override suspend fun invoke(
        weight: WeightModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext weightRepository.deleteWeight(
                weight = weight
            )
        }
}