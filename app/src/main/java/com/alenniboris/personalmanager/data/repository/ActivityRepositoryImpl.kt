package com.alenniboris.personalmanager.data.repository

import com.alenniboris.personalmanager.domain.model.ActivityModelDomain
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.repository.IActivityRepository
import java.util.Date

class ActivityRepositoryImpl : IActivityRepository {

    override suspend fun getActivitiesListByDate(
        date: Date,
        userId: String
    ): CustomResultModelDomain<List<ActivityModelDomain>, CommonExceptionModelDomain> {
        TODO("Not yet implemented")
    }

    override suspend fun addActivity(activity: ActivityModelDomain): CustomResultModelDomain<Unit, CommonExceptionModelDomain> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteActivity(activity: ActivityModelDomain): CustomResultModelDomain<Unit, CommonExceptionModelDomain> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllActivities(userId: String): CustomResultModelDomain<List<ActivityModelDomain>, CommonExceptionModelDomain> {
        TODO("Not yet implemented")
    }
}