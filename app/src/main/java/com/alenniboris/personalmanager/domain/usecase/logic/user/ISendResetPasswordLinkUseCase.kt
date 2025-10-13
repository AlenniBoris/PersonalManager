package com.alenniboris.personalmanager.domain.usecase.logic.user

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain

interface ISendResetPasswordLinkUseCase {
    suspend fun invoke(
        email: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}