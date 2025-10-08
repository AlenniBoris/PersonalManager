package com.alenniboris.personalmanager.domain.usecase.impl.weight

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import com.alenniboris.personalmanager.domain.repository.IUserRepository
import com.alenniboris.personalmanager.domain.repository.IWeightRepository
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.weight.IDeleteWeightUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteWeightUseCaseImpl @Inject constructor(
    private val weightRepository: IWeightRepository,
    private val userRepository: IUserRepository,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val dispatchers: IAppDispatchers
) : IDeleteWeightUseCase {

    override suspend fun invoke(
        weight: WeightModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext getCurrentUserUseCase.userFlow.value?.let { user ->
                when (
                    val weightResult = weightRepository.deleteWeight(
                        weight = weight
                    )
                ) {
                    is CustomResultModelDomain.Success -> {
                        when (
                            val weightsList = weightRepository.getAllWeights(
                                userId = user.id
                            )
                        ) {
                            is CustomResultModelDomain.Success -> {
                                userRepository.updateUser(
                                    user = user.copy(
                                        weight = weightsList.result.firstOrNull()?.weight ?: 0.0
                                    )
                                )
                            }

                            is CustomResultModelDomain.Error -> {
                                CustomResultModelDomain.Error(
                                    weightsList.exception
                                )
                            }
                        }
                    }

                    is CustomResultModelDomain.Error -> {
                        CustomResultModelDomain.Error(
                            weightResult.exception
                        )
                    }
                }
            } ?: CustomResultModelDomain.Error(
                CommonExceptionModelDomain.ErrorGettingData
            )
        }
}