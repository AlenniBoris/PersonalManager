package com.alenniboris.personalmanager.di

import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Singleton
    fun provideDispatchers(): IAppDispatchers = object : IAppDispatchers {

        override val IO: CoroutineDispatcher = Dispatchers.IO

        override val DEFAULT: CoroutineDispatcher = Dispatchers.Default

        override val MAIN: CoroutineDispatcher = Dispatchers.Main
    }
}