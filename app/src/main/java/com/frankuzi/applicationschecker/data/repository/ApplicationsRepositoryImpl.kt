package com.frankuzi.applicationschecker.data.repository

import com.frankuzi.applicationschecker.data.local.ApplicationsService
import com.frankuzi.applicationschecker.domain.model.ApplicationInfo
import com.frankuzi.applicationschecker.domain.repository.ApplicationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApplicationsRepositoryImpl @Inject constructor(
    private val applicationsService: ApplicationsService
) : ApplicationsRepository {
    override suspend fun getApplications(): Flow<List<ApplicationInfo>> {
        val applications = applicationsService.getAllInstalledApplication()
        return flow {
            emit(applications)
        }
    }

    override fun getApplicationByPackageName(packageName: String): ApplicationInfo? {
        return applicationsService.getApplicationInfoByPackageName(packageName)
    }
}