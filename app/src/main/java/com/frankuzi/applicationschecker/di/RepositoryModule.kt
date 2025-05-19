package com.frankuzi.applicationschecker.di

import com.frankuzi.applicationschecker.data.repository.ApplicationsRepositoryImpl
import com.frankuzi.applicationschecker.domain.repository.ApplicationsRepository
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
    abstract fun bindApplicationListRepository(
        applicationsListRepositoryImpl: ApplicationsRepositoryImpl
    ): ApplicationsRepository
}