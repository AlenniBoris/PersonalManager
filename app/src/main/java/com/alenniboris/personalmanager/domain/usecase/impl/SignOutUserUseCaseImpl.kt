package com.alenniboris.personalmanager.domain.usecase.impl

import com.alenniboris.personalmanager.domain.model.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.IAppDispatchers
import com.alenniboris.personalmanager.domain.repository.IUserRepository
import com.alenniboris.personalmanager.domain.usecase.logic.ISignOutUserUseCase
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