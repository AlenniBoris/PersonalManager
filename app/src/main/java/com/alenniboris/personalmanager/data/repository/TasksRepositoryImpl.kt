package com.alenniboris.personalmanager.data.repository

import com.alenniboris.personalmanager.data.mapper.toCommonException
import com.alenniboris.personalmanager.data.model.task.TaskModelData
import com.alenniboris.personalmanager.data.model.task.toModelData
import com.alenniboris.personalmanager.data.model.task.toModelDomain
import com.alenniboris.personalmanager.data.utils.CommonFunctions
import com.alenniboris.personalmanager.data.utils.FirebaseDatabaseValues
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.task.TaskModelDomain
import com.alenniboris.personalmanager.domain.repository.ITasksRepository
import com.alenniboris.personalmanager.domain.utils.GsonUtil.fromJson
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
    private val dispatchers: IAppDispatchers
) : ITasksRepository {

    override suspend fun getAllTasks(
        userId: String
    ): CustomResultModelDomain<List<TaskModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.requestListOfElements(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_TASKS,
                jsonMapping = { json ->
                    json.fromJson<TaskModelData>()
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

    override suspend fun updateTask(
        updatedTask: TaskModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.updateElementValue(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_TASKS,
                modelId = updatedTask.id,
                model = updatedTask.toModelData().toUpdatesMap(),
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }

    override suspend fun addTask(
        task: TaskModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.addRecordToTheTable(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_TASKS,
                exceptionMapping = { exception ->
                    exception.toCommonException()
                },
                onGeneratingError = {
                    CommonExceptionModelDomain.UnknownException
                },
                editingRecord = { id ->
                    task.copy(id = id).toModelData()
                }
            )
        }

    override suspend fun removeTask(
        taskId: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext CommonFunctions.removeRecordFromTheTable(
                dispatcher = dispatchers.IO,
                database = database,
                table = FirebaseDatabaseValues.TABLE_TASKS,
                recordId = taskId,
                exceptionMapping = { exception ->
                    exception.toCommonException()
                }
            )
        }
}