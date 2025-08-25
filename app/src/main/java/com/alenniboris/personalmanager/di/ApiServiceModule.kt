package com.alenniboris.personalmanager.di

import android.app.Application
import android.content.Context
import com.alenniboris.personalmanager.data.source.remote.service.location.ILocationGeoapifyApiService
import com.alenniboris.personalmanager.data.source.remote.service.weather.IWeatherMeteoblueApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideWeatherMeteoblueApiService(
        @ApplicationContext apl: Context
    ): IWeatherMeteoblueApiService =
        IWeatherMeteoblueApiService.get(
            apl = apl as Application
        )

    @Provides
    @Singleton
    fun provideLocationGeoapifyApiService(
        @ApplicationContext apl: Context
    ): ILocationGeoapifyApiService =
        ILocationGeoapifyApiService.get(
            apl = apl as Application
        )

}