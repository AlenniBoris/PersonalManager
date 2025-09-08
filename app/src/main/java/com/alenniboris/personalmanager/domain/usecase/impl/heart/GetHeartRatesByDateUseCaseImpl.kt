package com.alenniboris.personalmanager.domain.usecase.impl.heart

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.heart.HeartRateModelDomain
import com.alenniboris.personalmanager.domain.repository.IHeartRepository
import com.alenniboris.personalmanager.domain.usecase.logic.heart.IGetHeartRatesByDateUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class GetHeartRatesByDateUseCaseImpl @Inject constructor(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val heartRepository: IHeartRepository,
    private val dispatchers: IAppDispatchers
) : IGetHeartRatesByDateUseCase {

    override suspend fun invoke(
        date: Date
    ): CustomResultModelDomain<List<HeartRateModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext getCurrentUserUseCase.userFlow.value?.let { user ->
                heartRepository.getListOfRatesByDate(
                    date = date,
                    userId = user.id
                )
            } ?: CustomResultModelDomain.Error(
                CommonExceptionModelDomain.UnknownException
            )
        }
}