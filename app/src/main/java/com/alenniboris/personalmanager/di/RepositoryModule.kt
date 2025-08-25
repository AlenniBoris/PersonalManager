package com.alenniboris.personalmanager.di

import com.alenniboris.personalmanager.data.repository.LocationRepositoryImpl
import com.alenniboris.personalmanager.data.repository.UserRepositoryImpl
import com.alenniboris.personalmanager.data.repository.WeatherRepositoryImpl
import com.alenniboris.personalmanager.domain.repository.ILocationRepository
import com.alenniboris.personalmanager.domain.repository.IUserRepository
import com.alenniboris.personalmanager.domain.repository.IWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): IUserRepository

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): IWeatherRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): ILocationRepository
}