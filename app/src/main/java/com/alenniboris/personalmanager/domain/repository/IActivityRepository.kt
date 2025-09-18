package com.alenniboris.personalmanager.domain.repository

import com.alenniboris.personalmanager.domain.model.activity.ActivityModelDomain
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import java.util.Date

interface IActivityRepository {

    suspend fun getActivitiesListByDate(
        date: Date,
        userId: String
    ): CustomResultModelDomain<List<ActivityModelDomain>, CommonExceptionModelDomain>

    suspend fun addActivity(
        activity: ActivityModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>

    suspend fun deleteActivity(
        activity: ActivityModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>

    suspend fun getAllActivities(
        userId: String
    ): CustomResultModelDomain<List<ActivityModelDomain>, CommonExceptionModelDomain>
}