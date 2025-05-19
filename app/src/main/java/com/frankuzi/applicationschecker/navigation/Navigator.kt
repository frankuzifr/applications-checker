package com.frankuzi.applicationschecker.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

interface Navigator {
    val startDestination: Destination
    val navigationActions: SharedFlow<NavigationAction>

    suspend fun navigate(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )

    suspend fun navigateUp()
    suspend fun openActivity(packageName: String)
}

class DefaultNavigator @Inject constructor(
    override val startDestination: Destination,
): Navigator {
    private val _navigationActions = MutableSharedFlow<NavigationAction>()
    override val navigationActions: SharedFlow<NavigationAction> = _navigationActions.asSharedFlow()
    override suspend fun navigate(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit
    ) {
        _navigationActions.emit(NavigationAction.Navigate(
            destination = destination,
            navOptions = navOptions
        ))
    }

    override suspend fun navigateUp() {
        _navigationActions.emit(NavigationAction.NavigationUp)
    }

    override suspend fun openActivity(packageName: String) {
        _navigationActions.emit(NavigationAction.OpenActivity(packageName))
    }
}