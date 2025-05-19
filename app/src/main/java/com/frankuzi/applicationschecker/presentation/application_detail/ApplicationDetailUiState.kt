package com.frankuzi.applicationschecker.presentation.application_detail

data class ApplicationDetailUiState(
    val applicationName: String = "",
    val packageName: String = "",
    val version: String = "",
    val sum: String = ""
)