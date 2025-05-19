package com.frankuzi.applicationschecker.presentation.applications_list

import com.frankuzi.applicationschecker.domain.model.ApplicationInfo

data class ApplicationsListUiState(
    val isLoading: Boolean = false,
    val applications: List<ApplicationInfo> = emptyList()
)