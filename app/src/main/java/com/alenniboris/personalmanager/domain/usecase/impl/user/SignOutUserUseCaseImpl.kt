package com.alenniboris.personalmanager.domain.usecase.impl.user

import com.alenniboris.personalmanager.domain.repository.IUserRepository
import com.alenniboris.personalmanager.domain.usecase.logic.user.ISignOutUserUseCase
import javax.inject.Inject

class SignOutUserUseCaseImpl @Inject constructor(
    private val userRepository: IUserRepository
) : ISignOutUserUseCase {

    override fun invoke() = userRepository.signOut()
}