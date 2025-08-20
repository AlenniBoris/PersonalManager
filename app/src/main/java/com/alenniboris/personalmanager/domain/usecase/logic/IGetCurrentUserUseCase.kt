package com.alenniboris.personalmanager.domain.usecase.logic

import com.alenniboris.personalmanager.domain.model.UserModelDomain
import kotlinx.coroutines.flow.StateFlow

interface IGetCurrentUserUseCase {

    val userFlow: StateFlow<UserModelDomain?>
}