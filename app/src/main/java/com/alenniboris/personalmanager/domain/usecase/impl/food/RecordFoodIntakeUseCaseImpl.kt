package com.alenniboris.personalmanager.domain.usecase.impl.food

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.food.FoodIntakeModelDomain
import com.alenniboris.personalmanager.domain.repository.IFoodRepository
import com.alenniboris.personalmanager.domain.usecase.logic.food.IRecordFoodIntakeUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecordFoodIntakeUseCaseImpl @Inject constructor(
    private val foodRepository: IFoodRepository,
    private val dispatchers: IAppDispatchers,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase
) : IRecordFoodIntakeUseCase {

    override suspend fun invoke(
        foodIntake: FoodIntakeModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext getCurrentUserUseCase.userFlow.value?.let { user ->
                foodRepository.recordFoodIntake(
                    foodIntake = foodIntake.copy(userId = user.id)
                )
            } ?: CustomResultModelDomain.Error(
                CommonExceptionModelDomain.UnknownException
            )
        }
}