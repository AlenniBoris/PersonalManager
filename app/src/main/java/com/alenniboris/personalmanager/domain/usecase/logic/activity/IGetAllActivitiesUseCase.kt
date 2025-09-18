package com.alenniboris.personalmanager.domain.usecase.logic.activity

import com.alenniboris.personalmanager.domain.model.activity.ActivityModelDomain
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain

interface IGetAllActivitiesUseCase {

    suspend fun invoke(): CustomResultModelDomain<List<ActivityModelDomain>, CommonExceptionModelDomain>
}