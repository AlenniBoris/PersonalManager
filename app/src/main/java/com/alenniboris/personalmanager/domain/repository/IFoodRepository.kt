package com.alenniboris.personalmanager.domain.repository

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.food.FoodIntakeModelDomain
import java.util.Date

interface IFoodRepository {

    suspend fun getAllFoodIntakeByDate(
        date: Date,
        userId: String
    ): CustomResultModelDomain<List<FoodIntakeModelDomain>, CommonExceptionModelDomain>
}