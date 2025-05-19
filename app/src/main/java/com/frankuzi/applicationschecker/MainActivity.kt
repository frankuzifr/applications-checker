package com.frankuzi.applicationschecker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.frankuzi.applicationschecker.navigation.Navigator
import com.frankuzi.applicationschecker.ui.NavigationStack
import com.frankuzi.applicationschecker.ui.theme.ApplicationsCheckerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = getColor(R.color.transparent),
                darkScrim = getColor(R.color.transparent)
            )
        )
        setContent {
            ApplicationsCheckerTheme(
                dynamicColor = false
            ) {
                NavigationStack(
                    navigator = navigator
                )
            }
        }
    }
}