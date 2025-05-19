package com.frankuzi.applicationschecker.presentation

import androidx.lifecycle.ViewModel
import com.frankuzi.applicationschecker.navigation.Navigator
import javax.inject.Inject

abstract class BaseViewModel: ViewModel() {
    @Inject
    lateinit var navigator: Navigator
}