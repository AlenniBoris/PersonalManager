package com.alenniboris.personalmanager.domain.usecase.logic.user

import com.alenniboris.personalmanager.domain.model.user.UserModelDomain
import kotlinx.coroutines.flow.StateFlow

interface IGetCurrentUserUseCase {

    val userFlow: StateFlow<UserModelDomain?>
}