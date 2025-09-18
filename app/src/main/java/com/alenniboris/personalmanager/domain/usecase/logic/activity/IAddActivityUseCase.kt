package com.alenniboris.personalmanager.domain.usecase.logic.activity

import com.alenniboris.personalmanager.domain.model.ActivityModelDomain
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain

interface IAddActivityUseCase {

    suspend fun invoke(
        activity: ActivityModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}