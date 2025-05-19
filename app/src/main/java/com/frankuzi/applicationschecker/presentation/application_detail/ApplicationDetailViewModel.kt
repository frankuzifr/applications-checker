package com.frankuzi.applicationschecker.presentation.application_detail

import androidx.lifecycle.viewModelScope
import com.frankuzi.applicationschecker.domain.repository.ApplicationsRepository
import com.frankuzi.applicationschecker.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicationDetailViewModel @Inject constructor(
    private val applicationsListRepository: ApplicationsRepository
): BaseViewModel() {
    private val _state: MutableStateFlow<ApplicationDetailUiState> = MutableStateFlow(
        ApplicationDetailUiState()
    )
    val state: StateFlow<ApplicationDetailUiState> = _state.asStateFlow()

    private var packageName: String = ""

    fun loadInfoByPackageName(packageName: String) {
        this.packageName = packageName
        viewModelScope.launch {
            val applicationInfo = applicationsListRepository.getApplicationByPackageName(packageName)
            _state.update {
                it.copy(
                    applicationName = applicationInfo?.name ?: "",
                    packageName = applicationInfo?.packageName ?: "",
                    version = applicationInfo?.version ?: "",
                    sum = applicationInfo?.checkSum ?: "",
                )
            }
        }
    }

    fun onEvent(event: ApplicationDetailEvent) {
        when(event) {
            ApplicationDetailEvent.OnBackEvent -> {
                viewModelScope.launch {
                    navigator.navigateUp()
                }
            }
            ApplicationDetailEvent.OpenClickEvent -> {
                viewModelScope.launch {
                    navigator.openActivity(packageName)
                }
            }
        }
    }
}