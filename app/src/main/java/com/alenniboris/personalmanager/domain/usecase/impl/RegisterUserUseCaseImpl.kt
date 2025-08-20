package com.alenniboris.personalmanager.domain.usecase.impl

import com.alenniboris.personalmanager.domain.model.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.UserModelDomain
import com.alenniboris.personalmanager.domain.model.checkFieldsFilled
import com.alenniboris.personalmanager.domain.repository.IUserRepository
import com.alenniboris.personalmanager.domain.usecase.logic.IRegisterUserUseCase
import kotlinx.coroutines.withContext

class RegisterUserUseCaseImpl(
    private val userRepository: IUserRepository,
    private val dispatchers: IAppDispatchers
) : IRegisterUserUseCase {

    override suspend fun invoke(
        user: UserModelDomain,
        password: String,
        passwordCheck: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {

        if (!user.checkFieldsFilled()) {
            return@withContext CustomResultModelDomain.Error(
                CommonExceptionModelDomain.NotAllFieldsFilled
            )
        }

        if (password != passwordCheck) {
            return@withContext CustomResultModelDomain.Error(
                CommonExceptionModelDomain.PasswordAndCheckNotMatch
            )
        }

        return@withContext when (
            val registerResult = userRepository.registerUser(
                user = user,
                password = password
            )
        ) {
            is CustomResultModelDomain.Success -> {
                userRepository.loginUser(
                    email = user.email,
                    password = password
                )
            }

            is CustomResultModelDomain.Error -> {
                CustomResultModelDomain.Error(
                    registerResult.exception
                )
            }
        }
    }
}