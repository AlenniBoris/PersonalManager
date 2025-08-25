package com.alenniboris.personalmanager.domain.usecase.impl.user

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.repository.IUserRepository
import com.alenniboris.personalmanager.domain.usecase.logic.user.ILoginUserByEmailAndPasswordUseCase
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