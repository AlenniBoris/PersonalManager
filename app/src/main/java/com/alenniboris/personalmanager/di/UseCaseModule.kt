package com.alenniboris.personalmanager.di

import com.alenniboris.personalmanager.domain.usecase.impl.GetCurrentUserUseCaseImpl
import com.alenniboris.personalmanager.domain.usecase.impl.LoginUserByEmailAndPasswordUseCaseImpl
import com.alenniboris.personalmanager.domain.usecase.impl.RegisterUserUseCaseImpl
import com.alenniboris.personalmanager.domain.usecase.impl.SignOutUserUseCaseImpl
import com.alenniboris.personalmanager.domain.usecase.logic.IGetCurrentUserUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.ILoginUserByEmailAndPasswordUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.IRegisterUserUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.ISignOutUserUseCase
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
}