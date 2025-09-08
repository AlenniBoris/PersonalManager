package com.alenniboris.personalmanager.domain.usecase.impl.health

import com.alenniboris.personalmanager.domain.model.TodayHealthStatistics
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.repository.IFoodRepository
import com.alenniboris.personalmanager.domain.repository.IHeartRepository
import com.alenniboris.personalmanager.domain.usecase.logic.health.IGetTodayHealthStatistics
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class GetTodayHealthStatisticsImpl @Inject constructor(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val heartRepository: IHeartRepository,
    private val foodRepository: IFoodRepository,
    private val dispatchers: IAppDispatchers
) : IGetTodayHealthStatistics {

    override suspend fun invoke(
        date: Date
    ): CustomResultModelDomain<TodayHealthStatistics, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext getCurrentUserUseCase.userFlow.value?.let { user ->

                val currentWeight = user.weight
                val _heartRateDeffered = async {
                    heartRepository.getListOfRatesByDate(
                        date = date,
                        userId = user.id
                    )
                }
                val _foodListDeffered = async {
                    foodRepository.getAllFoodIntakeByDate(
                        date = date,
                        userId = user.id
                    )
                }

                val heartRateResult = _heartRateDeffered.await()
                val foodIntakeResult = _foodListDeffered.await()
                if (
                    heartRateResult is CustomResultModelDomain.Success &&
                    foodIntakeResult is CustomResultModelDomain.Success
                ) {

                    val heartRateList = heartRateResult.result
                    val foodIntakeList = foodIntakeResult.result

                    val averageHeartRate =
                        heartRateList.sumOf { it.heartRate.toDouble() } / heartRateList.size
                    val consumedCalories = foodIntakeList.sumOf { it.calories }
                    val consumedProteins = foodIntakeList.sumOf { it.proteins }
                    val consumedFats = foodIntakeList.sumOf { it.fats }
                    val consumedCarbohydrates = foodIntakeList.sumOf { it.carbohydrates }

                    CustomResultModelDomain.Success(
                        TodayHealthStatistics(
                            currentWeight = currentWeight,
                            averageHeartRate = averageHeartRate,
                            consumedCalories = consumedCalories,
                            caloriesIntake = user.caloriesIntake,
                            consumedProteins = consumedProteins,
                            proteinsIntake = user.neededProteins,
                            consumedFats = consumedFats,
                            fatsIntake = user.neededFats,
                            consumedCarbohydrates = consumedCarbohydrates,
                            carbohydratesIntake = user.neededCarbohydrates
                        )
                    )
                } else {
                    CustomResultModelDomain.Error(
                        CommonExceptionModelDomain.ErrorGettingData
                    )
                }
            } ?: CustomResultModelDomain.Error(
                CommonExceptionModelDomain.UnknownException
            )
        }
}