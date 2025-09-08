package com.alenniboris.personalmanager.domain.usecase.logic.food

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.food.FoodIntakeModelDomain
import java.util.Date

interface IGetFoodIntakeByDateUseCase {

    suspend fun invoke(
        date: Date
    ): CustomResultModelDomain<List<FoodIntakeModelDomain>, CommonExceptionModelDomain>
}