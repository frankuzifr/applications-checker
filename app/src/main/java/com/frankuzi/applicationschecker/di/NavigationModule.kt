package com.frankuzi.applicationschecker.di

import com.frankuzi.applicationschecker.navigation.DefaultNavigator
import com.frankuzi.applicationschecker.navigation.Destination
import com.frankuzi.applicationschecker.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {
    @Provides
    @Singleton
    fun providesNavigator(): Navigator {
        return DefaultNavigator(Destination.ListScreen)
    }
}