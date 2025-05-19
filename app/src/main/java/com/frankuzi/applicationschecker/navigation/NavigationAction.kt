package com.frankuzi.applicationschecker.navigation

import androidx.navigation.NavOptionsBuilder

sealed interface NavigationAction {
    data class Navigate(
        val destination: Destination,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ): NavigationAction
    object NavigationUp: NavigationAction
    class OpenActivity(val packageName: String): NavigationAction
}