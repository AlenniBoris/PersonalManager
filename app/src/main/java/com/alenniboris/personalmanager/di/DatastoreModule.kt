package com.alenniboris.personalmanager.di

import android.app.Application
import android.content.Context
import com.alenniboris.personalmanager.presentation.uikit.utils.DatastoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Provides
    @Singleton
    fun provideDatastore(@ApplicationContext context: Context): DatastoreRepository =
        DatastoreRepository(apl = context as Application)
}