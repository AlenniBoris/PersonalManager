package com.alenniboris.personalmanager.di

import com.alenniboris.personalmanager.domain.usecase.impl.tasks.AddTaskUseCaseImpl
import com.alenniboris.personalmanager.domain.usecase.impl.tasks.GetAllTasksUseCaseImpl
import com.alenniboris.personalmanager.domain.usecase.impl.tasks.RemoveTaskUseCaseImpl
import com.alenniboris.personalmanager.domain.usecase.impl.tasks.UpdateTaskUseCaseImpl
import com.alenniboris.personalmanager.domain.usecase.impl.user.GetCurrentUserUseCaseImpl
import com.alenniboris.personalmanager.domain.usecase.impl.user.LoginUserByEmailAndPasswordUseCaseImpl
import com.alenniboris.personalmanager.domain.usecase.impl.user.RegisterUserUseCaseImpl
import com.alenniboris.personalmanager.domain.usecase.impl.user.SignOutUserUseCaseImpl
import com.alenniboris.personalmanager.domain.usecase.impl.weather_and_location.GetCurrentForecastUseCaseImpl
import com.alenniboris.personalmanager.domain.usecase.impl.weather_and_location.GetTodayWeatherForecastUseCaseImpl
import com.alenniboris.personalmanager.domain.usecase.impl.weather_and_location.GetWeekWeatherForecastUseCaseImpl
import com.alenniboris.personalmanager.domain.usecase.logic.tasks.IAddTaskUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.tasks.IGetAllTasksUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.tasks.IRemoveTaskUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.tasks.IUpdateTaskUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.ILoginUserByEmailAndPasswordUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.IRegisterUserUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.ISignOutUserUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.weather_and_location.IGetCurrentForecastUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.weather_and_location.IGetTodayWeatherForecastUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.weather_and_location.IGetWeekWeatherForecastUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindGetCurrentUserUseCase(
        getCurrentUserUseCaseImpl: GetCurrentUserUseCaseImpl
    ): IGetCurrentUserUseCase

    @Binds
    abstract fun bindLoginUserByEmailAndPasswordUseCase(
        loginUserByEmailAndPasswordUseCaseImpl: LoginUserByEmailAndPasswordUseCaseImpl
    ): ILoginUserByEmailAndPasswordUseCase

    @Binds
    abstract fun bindRegisterUserUseCase(
        registerUserUseCaseImpl: RegisterUserUseCaseImpl
    ): IRegisterUserUseCase

    @Binds
    abstract fun bindSignOutUserUseCase(
        signOutUseCaseImpl: SignOutUserUseCaseImpl
    ): ISignOutUserUseCase

    @Binds
    abstract fun bindGetCurrentForecastUseCase(
        getCurrentForecastUseCaseImpl: GetCurrentForecastUseCaseImpl
    ): IGetCurrentForecastUseCase

    @Binds
    abstract fun bindGetTodayWeatherForecastUseCase(
        getTodayWeatherForecastUseCaseImpl: GetTodayWeatherForecastUseCaseImpl
    ): IGetTodayWeatherForecastUseCase

    @Binds
    abstract fun bindGetWeekWeatherForecastUseCase(
        getWeekWeatherForecastUseCaseImpl: GetWeekWeatherForecastUseCaseImpl
    ): IGetWeekWeatherForecastUseCase

    @Binds
    abstract fun bindGetAllTasksUseCase(
        getAllTasksUseCaseImpl: GetAllTasksUseCaseImpl
    ): IGetAllTasksUseCase

    @Binds
    abstract fun bindAllTaskUseCase(
        addTaskUseCaseImpl: AddTaskUseCaseImpl
    ): IAddTaskUseCase

    @Binds
    abstract fun bindRemoveTaskUseCase(
        removeTaskUseCaseImpl: RemoveTaskUseCaseImpl
    ): IRemoveTaskUseCase

    @Binds
    abstract fun bindUpdateTaskUseCase(
        updateTaskUseCaseImpl: UpdateTaskUseCaseImpl
    ): IUpdateTaskUseCase
}