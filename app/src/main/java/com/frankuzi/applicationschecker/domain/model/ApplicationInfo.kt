package com.frankuzi.applicationschecker.domain.model

data class ApplicationInfo(
    val name: String,
    val version: String,
    val packageName: String,
    val checkSum: String
)
