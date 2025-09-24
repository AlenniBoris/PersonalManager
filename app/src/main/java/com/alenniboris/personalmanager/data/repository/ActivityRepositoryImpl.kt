package com.alenniboris.personalmanager.data.repository

import com.alenniboris.personalmanager.data.mapper.toCommonException
import com.alenniboris.personalmanager.data.model.activity.ActivityModelData
import com.alenniboris.personalmanager.data.model.activity.toModelData
import com.alenniboris.personalmanager.data.model.activity.toModelDomain
import com.alenniboris.personalmanager.data.utils.CommonFunctions
import com.alenniboris.personalmanager.data.utils.FirebaseDatabaseValues
import com.alenniboris.personalmanager.domain.model.activity.ActivityModelDomain
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.repository.IActivityRepository
import com.alenniboris.personalmanager.domain.utils.GsonUtil.fromJson
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
    private val dispatchers: IAppDispatchers
) : IActivityRepository {

    override suspend fun getActivitiesListByDate(
        date: Date,
        userId: String
    ): CustomResultModelDomain<List<ActivityModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_ACTIVITY,
                jsonMapping = { json ->
                    json.fromJson<ActivityModelData>()
                },
                modelsMapping = { dataModel ->
                    dataModel.toModelDomain()
                },
                filterPredicate = { domainModel ->
                    domainModel.userId == userId && domainModel.markingDate == date
                },
                sortComparator = compareByDescending { it.markingTime },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun addActivity(
        activity: ActivityModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.addRecordToTheTable(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_ACTIVITY,
                exceptionMapping = { exception ->
                    exception.toCommonException()
                },
                onGeneratingError = {
                    CommonExceptionModelDomain.UnknownException
                },
                editingRecord = { id ->
                    activity.copy(id = id).toModelData()
                }
            )
        }

    override suspend fun deleteActivity(
        activity: ActivityModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.removeRecordFromTheTable(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_ACTIVITY,
                recordId = activity.id,
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getAllActivities(
        userId: String
    ): CustomResultModelDomain<List<ActivityModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_ACTIVITY,
                jsonMapping = { json ->
                    json.fromJson<ActivityModelData>()
                },
                modelsMapping = { dataModel ->
                    dataModel.toModelDomain()
                },
                filterPredicate = { domainModel ->
                    domainModel.userId == userId
                },
                sortComparator = compareByDescending { it.markingTime },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }
}