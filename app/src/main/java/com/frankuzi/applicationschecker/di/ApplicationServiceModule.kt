package com.frankuzi.applicationschecker.di

import android.content.Context
import com.frankuzi.applicationschecker.data.local.ApplicationsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationServiceModule {
    @Provides
    @Singleton
    fun providesApplicationService(
        @ApplicationContext context: Context
    ): ApplicationsService {
        return ApplicationsService(
            context = context
        )
    }
}