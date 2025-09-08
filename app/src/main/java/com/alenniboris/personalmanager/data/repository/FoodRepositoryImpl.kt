package com.alenniboris.personalmanager.data.repository

import com.alenniboris.personalmanager.data.mapper.toCommonException
import com.alenniboris.personalmanager.data.model.food.FoodIntakeModelData
import com.alenniboris.personalmanager.data.model.food.toModelData
import com.alenniboris.personalmanager.data.model.food.toModelDomain
import com.alenniboris.personalmanager.data.utils.CommonFunctions
import com.alenniboris.personalmanager.data.utils.FirebaseDatabaseValues
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.food.FoodIntakeModelDomain
import com.alenniboris.personalmanager.domain.repository.IFoodRepository
import com.alenniboris.personalmanager.domain.utils.GsonUtil.fromJson
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
    private val dispatchers: IAppDispatchers
) : IFoodRepository {

    override suspend fun getAllFoodIntakeByDate(
        date: Date,
        userId: String
    ): CustomResultModelDomain<List<FoodIntakeModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_FOOD,
                jsonMapping = { json -> json.fromJson<FoodIntakeModelData>() },
                modelsMapping = { dataModel -> dataModel.toModelDomain() },
                filterPredicate = { domainModel ->
                    domainModel.userId == userId
                },
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun recordFoodIntake(
        foodIntake: FoodIntakeModelDomain,
        userId: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.addRecordToTheTable(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_FOOD,
                exceptionMapping = { exception ->
                    exception.toCommonException()
                },
                onGeneratingError = {
                    CommonExceptionModelDomain.UnknownException
                },
                editingRecord = { newId ->
                    foodIntake.copy(id = newId).toModelData()
                }
            )
        }

    override suspend fun updateFoodIntake(
        foodIntake: FoodIntakeModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.updateElementValue(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_FOOD,
                modelId = foodIntake.id,
                model = foodIntake.toModelData().toUpdatesMap(),
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun removeFoodIntake(
        foodIntake: FoodIntakeModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.removeRecordFromTheTable(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_FOOD,
                recordId = foodIntake.id,
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }
}