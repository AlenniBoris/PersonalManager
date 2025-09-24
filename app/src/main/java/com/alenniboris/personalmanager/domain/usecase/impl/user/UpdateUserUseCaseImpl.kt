package com.alenniboris.personalmanager.domain.usecase.impl.user

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.user.UserModelDomain
import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import com.alenniboris.personalmanager.domain.repository.IUserRepository
import com.alenniboris.personalmanager.domain.repository.IWeightRepository
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.IUpdateUserUseCase
import com.alenniboris.personalmanager.domain.utils.stripTime
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

class UpdateUserUseCaseImpl @Inject constructor(
    private val userRepository: IUserRepository,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val weightRepository: IWeightRepository,
    private val dispatchers: IAppDispatchers
) : IUpdateUserUseCase {

    override suspend fun invoke(
        user: UserModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext getCurrentUserUseCase.userFlow.value?.let { oldUser ->

                when (
                    val res = userRepository.updateUser(
                        user = user
                    )
                ) {
                    is CustomResultModelDomain.Success -> {
                        if (oldUser.weight == user.weight) {
                            res
                        } else {
                            weightRepository.addWeight(
                                weight = WeightModelDomain(
                                    id = "",
                                    userId = user.id,
                                    weight = user.weight,
                                    markingDate = Calendar.getInstance().time.stripTime(),
                                    markingTime = Calendar.getInstance().time
                                )
                            )
                        }
                    }

                    is CustomResultModelDomain.Error -> {
                        CustomResultModelDomain.Error(
                            res.exception
                        )
                    }
                }
            } ?: CustomResultModelDomain.Error(
                CommonExceptionModelDomain.UnknownException
            )
        }
}