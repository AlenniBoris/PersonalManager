package com.alenniboris.personalmanager.domain.usecase.impl.user

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.repository.IUserRepository
import com.alenniboris.personalmanager.domain.usecase.logic.user.ISignOutUserUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignOutUserUseCaseImpl @Inject constructor(
    private val userRepository: IUserRepository,
    private val dispatchers: IAppDispatchers
) : ISignOutUserUseCase {

    override suspend fun invoke(): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext userRepository.signOut()
        }
}