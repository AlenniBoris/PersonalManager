package com.alenniboris.personalmanager.data.utils

import android.util.Log
import com.alenniboris.personalmanager.domain.model.CustomResultModelDomain
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

object CommonFunctions {

    suspend inline fun <reified SavingModel, ExceptionModel> addRecordToTheTable(
        dispatcher: CoroutineDispatcher,
        database: FirebaseDatabase,
        table: String,
        crossinline exceptionMapping: (Throwable) -> ExceptionModel,
        crossinline onGeneratingError: () -> ExceptionModel,
        crossinline editingRecord: (newId: String) -> SavingModel,
    ): CustomResultModelDomain<Unit, ExceptionModel> = withContext(dispatcher) {
        runCatching {

            val tableRef = database.getReference(table)
            val newTableRef = tableRef.push()

            val newRecordId =
                newTableRef.key ?: return@withContext CustomResultModelDomain.Error(
                    onGeneratingError()
                )

            val editedRecord = editingRecord(newRecordId)

            newTableRef.setValue(editedRecord)

            return@withContext CustomResultModelDomain.Success(Unit)
        }.getOrElse { exception ->
            Log.e("!!!", exception.stackTraceToString())
            return@withContext CustomResultModelDomain.Error(
                exceptionMapping(exception)
            )
        }
    }

    suspend inline fun <DataModel, ExceptionModel> updateElementValue(
        dispatcher: CoroutineDispatcher,
        database: FirebaseDatabase,
        table: String,
        modelId: String,
        model: DataModel,
        crossinline exceptionMapping: (Throwable) -> ExceptionModel
    ): CustomResultModelDomain<Unit, ExceptionModel> = withContext(dispatcher) {
        runCatching {

            val elementRef = database.getReference(table + "/${modelId}")
            Log.e("!!!!", elementRef.key.toString())
            elementRef.setValue(model)

            return@withContext CustomResultModelDomain.Success(Unit)
        }.getOrElse { exception ->
            Log.e("!!!", exception.stackTraceToString())
            return@withContext CustomResultModelDomain.Error(
                exceptionMapping(exception)
            )
        }
    }

    suspend inline fun <reified DataModel, DomainModel, ExceptionModel> requestElementByField(
        field: String,
        fieldValue: String,
        dispatcher: CoroutineDispatcher,
        database: FirebaseDatabase,
        table: String,
        crossinline resultMapping: (DataModel?) -> DomainModel?,
        crossinline exceptionMapping: (Throwable) -> ExceptionModel
    ): CustomResultModelDomain<DomainModel?, ExceptionModel> = withContext(dispatcher) {
        runCatching {

            val tableSnapshot = database.reference
                .child(table)
                .get()
                .await()

            val elementSnapshot = tableSnapshot.children.toList()
                .firstOrNull { it.child(field).value == fieldValue }

            val result = elementSnapshot?.getValue(DataModel::class.java)

            return@withContext CustomResultModelDomain.Success(
                resultMapping(result)
            )
        }.getOrElse { exception ->
            Log.e("!!!", exception.stackTraceToString())
            return@withContext CustomResultModelDomain.Error(
                exceptionMapping(exception)
            )
        }
    }
}
