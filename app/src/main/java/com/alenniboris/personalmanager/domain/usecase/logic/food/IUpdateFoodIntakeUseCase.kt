package com.alenniboris.personalmanager.domain.usecase.logic.food

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.food.FoodIntakeModelDomain

interface IUpdateFoodIntakeUseCase {

    suspend fun invoke(
        updatedIntake: FoodIntakeModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}