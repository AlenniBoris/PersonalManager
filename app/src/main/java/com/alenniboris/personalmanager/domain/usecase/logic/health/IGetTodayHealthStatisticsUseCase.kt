package com.alenniboris.personalmanager.domain.usecase.logic.health

import com.alenniboris.personalmanager.domain.model.TodayHealthStatisticsModelDomain
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import java.util.Date

interface IGetTodayHealthStatisticsUseCase {

    suspend fun invoke(
        date: Date
    ): CustomResultModelDomain<TodayHealthStatisticsModelDomain, CommonExceptionModelDomain>
}