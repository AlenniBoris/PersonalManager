package com.alenniboris.personalmanager.domain.usecase.logic.activity

import com.alenniboris.personalmanager.domain.model.ActivityModelDomain
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import java.util.Date

interface IGetActivitiesByDateUseCase {

    suspend fun invoke(
        date: Date
    ): CustomResultModelDomain<List<ActivityModelDomain>, CommonExceptionModelDomain>
}