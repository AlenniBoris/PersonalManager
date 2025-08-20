package com.alenniboris.personalmanager.domain.usecase.impl

import com.alenniboris.personalmanager.domain.model.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.IAppDispatchers
import com.alenniboris.personalmanager.domain.repository.IUserRepository
import com.alenniboris.personalmanager.domain.usecase.logic.ILoginUserByEmailAndPasswordUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUserByEmailAndPasswordUseCaseImpl @Inject constructor(
    private val userRepository: IUserRepository,
    private val dispatchers: IAppDispatchers
) : ILoginUserByEmailAndPasswordUseCase {

    override suspend fun invoke(
        email: String,
        password: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {

        if (email.isEmpty() || password.isEmpty()) {
            return@withContext CustomResultModelDomain.Error(
                CommonExceptionModelDomain.NotAllFieldsFilled
            )
        }

        return@withContext userRepository.loginUser(
            email = email,
            password = password
        )
    }
}