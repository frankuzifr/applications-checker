package com.frankuzi.applicationschecker.data.local

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.frankuzi.applicationschecker.domain.model.ApplicationInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest
import javax.inject.Inject

class ApplicationsService @Inject constructor(
    private val context: Context
) {
    private val applicationsInfo = mutableListOf<ApplicationInfo>()

    suspend fun getAllInstalledApplication(): List<ApplicationInfo> {
        applicationsInfo.clear()
        return withContext(Dispatchers.IO) {
            val pm = context.packageManager
            val appInfos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pm.getInstalledApplications(PackageManager.ApplicationInfoFlags.of(0L))
            } else {
                pm.getInstalledApplications(0)
            }
            return@withContext appInfos.map {
                val label = withContext(Dispatchers.IO) {
                    it.loadLabel(pm)
                }
                val packageInfo = pm.getPackageInfo(it.packageName, 0)
                val sum = calculateChecksum(it.sourceDir)
                val applicationInfo = ApplicationInfo(
                    name = label.toString(),
                    version = packageInfo?.versionName ?: "Unknown version",
                    packageName = it.packageName ?: "Unknown package name",
                    checkSum = sum
                )
                applicationsInfo.add(applicationInfo)
                applicationInfo
            }
        }
    }

    fun getApplicationInfoByPackageName(packageName: String): ApplicationInfo? {
        return applicationsInfo.firstOrNull {
            it.packageName == packageName
        }
    }

    private suspend fun calculateChecksum(
        path: String
    ): String {
        return withContext(Dispatchers.IO) {
            val messageDigest = MessageDigest.getInstance("SHA-256")

            val fis = FileInputStream(File(path))
            val buffer = ByteArray(8192)

            try {
                var bytesRead = fis.read(buffer)
                while (bytesRead != -1) {
                    messageDigest.update(buffer, 0, bytesRead)
                    bytesRead = fis.read(buffer)
                }

                return@withContext messageDigest.digest().joinToString("") {
                    "%02x".format(it)
                }
            } finally {
                fis.close()
            }
        }
    }
}