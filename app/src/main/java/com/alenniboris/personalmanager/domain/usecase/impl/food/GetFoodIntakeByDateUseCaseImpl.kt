package com.alenniboris.personalmanager.domain.usecase.impl.food

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.food.FoodIntakeModelDomain
import com.alenniboris.personalmanager.domain.repository.IFoodRepository
import com.alenniboris.personalmanager.domain.usecase.logic.food.IGetFoodIntakeByDateUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class GetFoodIntakeByDateUseCaseImpl @Inject constructor(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val foodRepository: IFoodRepository,
    private val dispatchers: IAppDispatchers
) : IGetFoodIntakeByDateUseCase {

    override suspend fun invoke(
        date: Date
    ): CustomResultModelDomain<List<FoodIntakeModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext getCurrentUserUseCase.userFlow.value?.let { user ->
                foodRepository.getAllFoodIntakeByDate(
                    date = date,
                    userId = user.id
                )
            } ?: CustomResultModelDomain.Error(
                CommonExceptionModelDomain.UnknownException
            )
        }
}