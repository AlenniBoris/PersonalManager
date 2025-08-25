package com.alenniboris.personalmanager.domain.repository

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.user.UserModelDomain
import kotlinx.coroutines.flow.StateFlow

interface IUserRepository {

    val userFlow: StateFlow<UserModelDomain?>

    suspend fun loginUser(
        email: String,
        password: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>

    suspend fun registerUser(
        user: UserModelDomain,
        password: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>

    suspend fun signOut(): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}