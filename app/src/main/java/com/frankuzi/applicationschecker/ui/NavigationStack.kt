package com.frankuzi.applicationschecker.ui

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.frankuzi.applicationschecker.navigation.Destination
import com.frankuzi.applicationschecker.navigation.NavigationAction
import com.frankuzi.applicationschecker.navigation.Navigator
import com.frankuzi.applicationschecker.presentation.application_detail.components.ApplicationDetailRoot
import com.frankuzi.applicationschecker.presentation.applications_list.components.ApplicationsListRoot

@Composable
fun NavigationStack(
    navigator: Navigator
) {
    val context = LocalContext.current
    val activity = LocalActivity.current
    val navController = rememberNavController()
    ObserveAsEvent(
        flow = navigator.navigationActions
    ) { action ->
        when(action) {
            is NavigationAction.Navigate -> navController.navigate(
                action.destination
            ) {
                action.navOptions(this)
            }
            NavigationAction.NavigationUp -> {
                navController.navigateUp()
            }
            is NavigationAction.OpenActivity -> {
                val launchIntent = context.packageManager.getLaunchIntentForPackage(action.packageName)
                context.startActivity(launchIntent)
            }
        }
    }
    BackHandler {
        val isSuccess = navController.popBackStack()
        if (!isSuccess)
            activity?.finish()
    }
    NavHost(
        navController = navController,
        startDestination = navigator.startDestination
    ) {
        composable<Destination.ListScreen>(
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(350)
                )
            },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(350)
                )
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(350)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(350)
                )
            }
        ) {
            ApplicationsListRoot()
        }
        composable<Destination.DetailScreen>(
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(350)
                )
            },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(350)
                )
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(350)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(350)
                )
            }
        ) {
            val args = it.toRoute<Destination.DetailScreen>()
            ApplicationDetailRoot(args.packageName)
        }
    }
}