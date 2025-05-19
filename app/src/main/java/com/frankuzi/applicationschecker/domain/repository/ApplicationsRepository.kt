package com.frankuzi.applicationschecker.domain.repository

import com.frankuzi.applicationschecker.domain.model.ApplicationInfo
import kotlinx.coroutines.flow.Flow

interface ApplicationsRepository {
    suspend fun getApplications(): Flow<List<ApplicationInfo>>
    fun getApplicationByPackageName(packageName: String): ApplicationInfo?
}