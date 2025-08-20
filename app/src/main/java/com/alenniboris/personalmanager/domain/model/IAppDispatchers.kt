package com.alenniboris.personalmanager.domain.model

import kotlinx.coroutines.CoroutineDispatcher

interface IAppDispatchers {

    val IO : CoroutineDispatcher

    val DEFAULT : CoroutineDispatcher

    val MAIN : CoroutineDispatcher
}