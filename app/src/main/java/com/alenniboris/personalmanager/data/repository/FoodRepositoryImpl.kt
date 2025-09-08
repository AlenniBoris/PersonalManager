package com.alenniboris.personalmanager.data.repository

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.food.FoodIntakeModelDomain
import com.alenniboris.personalmanager.domain.repository.IFoodRepository
import java.util.Date

class FoodRepositoryImpl : IFoodRepository {

    override suspend fun getAllFoodIntakeByDate(
        date: Date,
        userId: String
    ): CustomResultModelDomain<List<FoodIntakeModelDomain>, CommonExceptionModelDomain> {
        TODO("Not yet implemented")
    }
}