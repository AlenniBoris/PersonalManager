package com.alenniboris.personalmanager.domain.usecase.impl

import com.alenniboris.personalmanager.domain.model.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.IAppDispatchers
import com.alenniboris.personalmanager.domain.repository.IUserRepository
import com.alenniboris.personalmanager.domain.usecase.logic.ISignOutUseCase
import kotlinx.coroutines.withContext

class SignOutUseCaseImpl(
    private val userRepository: IUserRepository,
    private val dispatchers: IAppDispatchers
) : ISignOutUseCase {

    override suspend fun invoke(): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext userRepository.signOut()
        }
}