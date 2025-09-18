package com.alenniboris.personalmanager.domain.usecase.impl.weight

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import com.alenniboris.personalmanager.domain.repository.IWeightRepository
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.weight.IGetWeightsByDateUseCase
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class GetWeightsByDateUseCaseImpl @Inject constructor(
    private val weightRepository: IWeightRepository,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val dispatchers: IAppDispatchers
) : IGetWeightsByDateUseCase {

    override suspend fun invoke(
        date: Date
    ): CustomResultModelDomain<List<WeightModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext getCurrentUserUseCase.userFlow.value?.let { user ->
                weightRepository.getWeightsListByDateRange(
                    startDate = date,
                    endDate = date,
                    userId = user.id
                )
            } ?: CustomResultModelDomain.Error(
                CommonExceptionModelDomain.ErrorGettingData
            )
        }
}