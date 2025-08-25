package com.alenniboris.personalmanager.domain.model.common

import kotlinx.coroutines.CoroutineDispatcher

interface IAppDispatchers {
    val IO : CoroutineDispatcher
    val DEFAULT : CoroutineDispatcher
    val MAIN : CoroutineDispatcher
}