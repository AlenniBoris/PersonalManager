package com.alenniboris.personalmanager.di

import com.alenniboris.personalmanager.data.repository.FoodRepositoryImpl
import com.alenniboris.personalmanager.data.repository.HeartRepositoryImpl
import com.alenniboris.personalmanager.data.repository.LocationRepositoryImpl
import com.alenniboris.personalmanager.data.repository.TasksRepositoryImpl
import com.alenniboris.personalmanager.data.repository.UserRepositoryImpl
import com.alenniboris.personalmanager.data.repository.WeatherRepositoryImpl
import com.alenniboris.personalmanager.data.repository.WeightRepositoryImpl
import com.alenniboris.personalmanager.domain.repository.IFoodRepository
import com.alenniboris.personalmanager.domain.repository.IHeartRepository
import com.alenniboris.personalmanager.domain.repository.ILocationRepository
import com.alenniboris.personalmanager.domain.repository.ITasksRepository
import com.alenniboris.personalmanager.domain.repository.IUserRepository
import com.alenniboris.personalmanager.domain.repository.IWeatherRepository
import com.alenniboris.personalmanager.domain.repository.IWeightRepository
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

    @Binds
    @Singleton
    abstract fun bindTasksRepository(tasksRepositoryImpl: TasksRepositoryImpl): ITasksRepository

    @Binds
    @Singleton
    abstract fun bindWeightRepository(weightRepositoryImpl: WeightRepositoryImpl): IWeightRepository

    @Binds
    @Singleton
    abstract fun bindHeartRepository(heartRepositoryImpl: HeartRepositoryImpl): IHeartRepository

    @Binds
    @Singleton
    abstract fun bindFoodRepository(foodRepositoryImpl: FoodRepositoryImpl): IFoodRepository
}