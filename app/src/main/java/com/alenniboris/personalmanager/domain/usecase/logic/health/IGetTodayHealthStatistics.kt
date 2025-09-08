package com.alenniboris.personalmanager.domain.usecase.logic.health

import com.alenniboris.personalmanager.domain.model.TodayHealthStatistics
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import java.util.Date

interface IGetTodayHealthStatistics {

    suspend fun invoke(
        date: Date
    ): CustomResultModelDomain<TodayHealthStatistics, CommonExceptionModelDomain>
}