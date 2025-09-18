package com.alenniboris.personalmanager.data.repository

import com.alenniboris.personalmanager.data.mapper.toCommonException
import com.alenniboris.personalmanager.data.model.heart.HeartRateModelData
import com.alenniboris.personalmanager.data.model.heart.toModelData
import com.alenniboris.personalmanager.data.model.heart.toModelDomain
import com.alenniboris.personalmanager.data.utils.CommonFunctions
import com.alenniboris.personalmanager.data.utils.FirebaseDatabaseValues
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.heart.HeartRateModelDomain
import com.alenniboris.personalmanager.domain.repository.IHeartRepository
import com.alenniboris.personalmanager.domain.utils.GsonUtil.fromJson
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class HeartRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
    private val dispatchers: IAppDispatchers
) : IHeartRepository {

    override suspend fun getListOfRatesByDate(
        date: Date,
        userId: String
    ): CustomResultModelDomain<List<HeartRateModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_HEART_RATE,
                jsonMapping = { json ->
                    json.fromJson<HeartRateModelData>()
                },
                modelsMapping = { dataModel ->
                    dataModel.toModelDomain()
                },
                filterPredicate = { domainModel ->
                    domainModel.userId == userId && domainModel.markingDate == date
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun addHeartRate(
        heartRate: HeartRateModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.addRecordToTheTable(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_HEART_RATE,
                exceptionMapping = { exception ->
                    exception.toCommonException()
                },
                onGeneratingError = {
                    CommonExceptionModelDomain.UnknownException
                },
                editingRecord = { id ->
                    heartRate.copy(id = id).toModelData()
                }
            )
        }

    override suspend fun deleteHeartRate(
        heartRate: HeartRateModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.removeRecordFromTheTable(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_HEART_RATE,
                recordId = heartRate.id,
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getAllHeartRates(
        userId: String
    ): CustomResultModelDomain<List<HeartRateModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_HEART_RATE,
                jsonMapping = { json ->
                    json.fromJson<HeartRateModelData>()
                },
                modelsMapping = { dataModel ->
                    dataModel.toModelDomain()
                },
                filterPredicate = { domainModel ->
                    domainModel.userId == userId
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }
}