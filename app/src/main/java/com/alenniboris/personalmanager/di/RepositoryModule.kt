package com.alenniboris.personalmanager.di

import com.alenniboris.personalmanager.data.repository.UserRepositoryImpl
import com.alenniboris.personalmanager.domain.repository.IUserRepository
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
}