package com.alenniboris.personalmanager.data.repository

import com.alenniboris.personalmanager.data.mapper.toCommonException
import com.alenniboris.personalmanager.data.model.user.UserModelData
import com.alenniboris.personalmanager.data.model.user.toModelData
import com.alenniboris.personalmanager.data.model.user.toModelDomain
import com.alenniboris.personalmanager.data.utils.CommonFunctions
import com.alenniboris.personalmanager.data.utils.FirebaseDatabaseValues
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.user.UserModelDomain
import com.alenniboris.personalmanager.domain.repository.IUserRepository
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase,
    private val dispatchers: IAppDispatchers
) : IUserRepository {

    private val _userFlow = MutableStateFlow<UserModelDomain?>(null)
    override val userFlow: StateFlow<UserModelDomain?> = _userFlow.asStateFlow()

    override suspend fun loginUser(
        email: String,
        password: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        runCatching {

            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
            if (firebaseUser == null) {
                return@withContext CustomResultModelDomain.Error(
                    CommonExceptionModelDomain.ErrorGettingData
                )
            }

            return@withContext when (
                val databaseUserResult = CommonFunctions.requestElementByField(
                    field = FirebaseDatabaseValues.FIELD_EMAIL,
                    fieldValue = email,
                    dispatcher = dispatchers.IO,
                    database = database,
                    table = FirebaseDatabaseValues.TABLE_USERS,
                    resultMapping = { dataModel: UserModelData? ->
                        dataModel
                    },
                    exceptionMapping = { exception ->
                        exception.toCommonException()
                    }
                )
            ) {
                is CustomResultModelDomain.Success -> {

                    val databaseUser = databaseUserResult.result
                    databaseUser?.let {
                        _userFlow.update {
                            databaseUser.toModelDomain()
                        }
                        CustomResultModelDomain.Success(Unit)
                    } ?: return@withContext CustomResultModelDomain.Error(
                        CommonExceptionModelDomain.NoSuchUser
                    )
                }

                is CustomResultModelDomain.Error -> {
                    return@withContext CustomResultModelDomain.Error(
                        databaseUserResult.exception
                    )
                }
            }
        }.getOrElse {
            LogPrinter.printLog(
                tag = "!!!",
                message = """
                    loginUser , 
                    ${it.stackTraceToString()}
                """.trimIndent()
            )
            return@withContext CustomResultModelDomain.Error(
                it.toCommonException()
            )
        }
    }

    override suspend fun registerUser(
        user: UserModelDomain,
        password: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> = withContext(dispatchers.IO) {
        runCatching {

            val authResult = auth.createUserWithEmailAndPassword(user.email, password).await()
            val firebaseUser = authResult.user
            if (firebaseUser == null) {
                return@withContext CustomResultModelDomain.Error(
                    CommonExceptionModelDomain.ErrorGettingData
                )
            }

            return@withContext when (
                val registrationResult = CommonFunctions.addRecordToTheTable(
                    dispatcher = dispatchers.IO,
                    database = database,
                    table = FirebaseDatabaseValues.TABLE_USERS,
                    exceptionMapping = { exception ->
                        exception.toCommonException()
                    },
                    onGeneratingError = {
                        CommonExceptionModelDomain.ErrorGettingData
                    },
                    editingRecord = { newId ->
                        user.copy(id = newId).toModelData()
                    }
                )
            ) {

                is CustomResultModelDomain.Success -> {
                    CustomResultModelDomain.Success(Unit)
                }

                is CustomResultModelDomain.Error -> {
                    CustomResultModelDomain.Error(
                        registrationResult.exception
                    )
                }
            }
        }.getOrElse {
            LogPrinter.printLog(
                tag = "!!!",
                message = """
                    registerUser , 
                    ${it.stackTraceToString()}
                """.trimIndent()
            )
            return@withContext CustomResultModelDomain.Error(
                it.toCommonException()
            )
        }
    }

    override fun signOut() {
        _userFlow.update { null }
        auth.signOut()
    }

    override suspend fun updateUser(
        user: UserModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext when (
                val res = CommonFunctions.updateElementValue(
                    dispatcher = dispatchers.IO,
                    database = database,
                    table = FirebaseDatabaseValues.TABLE_USERS,
                    modelId = user.id,
                    model = user.toModelData(),
                    exceptionMapping = { exception ->
                        exception.toCommonException()
                    }
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _userFlow.update { user }
                    res
                }

                is CustomResultModelDomain.Error -> {
                    res
                }
            }
        }

    override suspend fun sendPasswordResetLink(
        email: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {
                val result = auth.sendPasswordResetEmail(email).await()
                return@withContext CustomResultModelDomain.Success(Unit)
            }.getOrElse {
                LogPrinter.printLog(
                    tag = "!!!",
                    message = """
                    registerUser , 
                    ${it.stackTraceToString()}
                """.trimIndent()
                )
                return@withContext CustomResultModelDomain.Error(
                    it.toCommonException()
                )
            }
        }
}