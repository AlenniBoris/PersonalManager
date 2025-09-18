package com.alenniboris.personalmanager.data.repository

import com.alenniboris.personalmanager.data.mapper.toCommonException
import com.alenniboris.personalmanager.data.model.weight.WeightModelData
import com.alenniboris.personalmanager.data.model.weight.toModelData
import com.alenniboris.personalmanager.data.model.weight.toModelDomain
import com.alenniboris.personalmanager.data.utils.CommonFunctions
import com.alenniboris.personalmanager.data.utils.FirebaseDatabaseValues
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import com.alenniboris.personalmanager.domain.repository.IWeightRepository
import com.alenniboris.personalmanager.domain.utils.GsonUtil.fromJson
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class WeightRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
    private val dispatchers: IAppDispatchers
) : IWeightRepository {

    override suspend fun getWeightsListByDateRange(
        startDate: Date,
        endDate: Date,
        userId: String
    ): CustomResultModelDomain<List<WeightModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_WEIGHT,
                jsonMapping = { json ->
                    json.fromJson<WeightModelData>()
                },
                modelsMapping = { dataModel ->
                    dataModel.toModelDomain()
                },
                filterPredicate = { domainModel ->
                    (domainModel.userId == userId) &&
                            (domainModel.markingDate.time >= startDate.time &&
                                    domainModel.markingDate.time <= endDate.time)
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun addWeight(
        weight: WeightModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.addRecordToTheTable(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_WEIGHT,
                exceptionMapping = { exception ->
                    exception.toCommonException()
                },
                onGeneratingError = {
                    CommonExceptionModelDomain.UnknownException
                },
                editingRecord = { id ->
                    weight.copy(id = id).toModelData()
                }
            )
        }

    override suspend fun deleteWeight(
        weight: WeightModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.removeRecordFromTheTable(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_WEIGHT,
                recordId = weight.id,
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun getAllWeights(
        userId: String
    ): CustomResultModelDomain<List<WeightModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_WEIGHT,
                jsonMapping = { json ->
                    json.fromJson<WeightModelData>()
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