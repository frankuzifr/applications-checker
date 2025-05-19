package com.frankuzi.applicationschecker.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    object ListScreen: Destination
    @Serializable
    class DetailScreen(val packageName: String): Destination
}