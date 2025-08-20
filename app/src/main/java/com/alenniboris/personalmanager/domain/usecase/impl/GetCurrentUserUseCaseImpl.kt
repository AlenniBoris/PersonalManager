package com.alenniboris.personalmanager.domain.usecase.impl

import com.alenniboris.personalmanager.domain.model.UserModelDomain
import com.alenniboris.personalmanager.domain.repository.IUserRepository
import com.alenniboris.personalmanager.domain.usecase.logic.IGetCurrentUserUseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCurrentUserUseCaseImpl @Inject constructor(
    private val userRepository: IUserRepository
) : IGetCurrentUserUseCase {

    override val userFlow: StateFlow<UserModelDomain?> = userRepository.userFlow
}