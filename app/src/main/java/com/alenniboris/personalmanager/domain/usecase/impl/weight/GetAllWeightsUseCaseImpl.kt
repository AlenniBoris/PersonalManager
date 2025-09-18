package com.alenniboris.personalmanager.domain.usecase.impl.weight

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import com.alenniboris.personalmanager.domain.repository.IWeightRepository
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.weight.IGetAllWeightsUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllWeightsUseCaseImpl @Inject constructor(
    private val weightRepository: IWeightRepository,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val dispatchers: IAppDispatchers
) : IGetAllWeightsUseCase {

    override suspend fun invoke(): CustomResultModelDomain<List<WeightModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext getCurrentUserUseCase.userFlow.value?.let { user ->
                weightRepository.getAllWeights(
                    userId = user.id
                )
            } ?: CustomResultModelDomain.Error(
                CommonExceptionModelDomain.ErrorGettingData
            )
        }
}