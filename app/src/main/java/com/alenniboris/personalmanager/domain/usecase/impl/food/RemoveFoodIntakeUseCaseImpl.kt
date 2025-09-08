package com.alenniboris.personalmanager.domain.usecase.impl.food

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.food.FoodIntakeModelDomain
import com.alenniboris.personalmanager.domain.repository.IFoodRepository
import com.alenniboris.personalmanager.domain.usecase.logic.food.IRemoveFoodIntakeUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoveFoodIntakeUseCaseImpl @Inject constructor(
    private val foodRepository: IFoodRepository,
    private val dispatchers: IAppDispatchers
) : IRemoveFoodIntakeUseCase {

    override suspend fun invoke(
        foodIntake: FoodIntakeModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext foodRepository.removeFoodIntake(
                foodIntake = foodIntake
            )
        }
}