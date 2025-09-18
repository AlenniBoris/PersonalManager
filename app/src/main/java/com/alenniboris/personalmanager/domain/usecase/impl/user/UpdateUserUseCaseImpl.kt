package com.alenniboris.personalmanager.domain.usecase.impl.user

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.user.UserModelDomain
import com.alenniboris.personalmanager.domain.repository.IUserRepository
import com.alenniboris.personalmanager.domain.usecase.logic.user.IUpdateUserUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateUserUseCaseImpl @Inject constructor(
    private val userRepository: IUserRepository,
    private val dispatchers: IAppDispatchers
) : IUpdateUserUseCase {

    override suspend fun invoke(
        user: UserModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext userRepository.updateUser(
                user = user
            )
        }
}