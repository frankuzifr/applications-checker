package com.frankuzi.applicationschecker.presentation.applications_list

import androidx.lifecycle.viewModelScope
import com.frankuzi.applicationschecker.domain.repository.ApplicationsRepository
import com.frankuzi.applicationschecker.navigation.Destination
import com.frankuzi.applicationschecker.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicationsListViewModel @Inject constructor(
    private val applicationsListRepository: ApplicationsRepository
): BaseViewModel() {
    private val _state: MutableStateFlow<ApplicationsListUiState> = MutableStateFlow(
        ApplicationsListUiState()
    )
    val state: StateFlow<ApplicationsListUiState> = _state.asStateFlow()

    init {
        updateApplicationsInfo()
    }

    fun onEvent(applicationsListEvent: ApplicationsListEvent) {
        when(applicationsListEvent) {
            is ApplicationsListEvent.ApplicationClickEvent -> {
                viewModelScope.launch {
                    navigator.navigate(
                        destination = Destination.DetailScreen(
                            packageName = applicationsListEvent.applicationInfo.packageName
                        ),
                        navOptions = {}
                    )
                }
            }
        }
    }

    private fun updateApplicationsInfo() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            applicationsListRepository.getApplications()
                .collect { applications ->
                    _state.update {
                        it.copy(
                            applications = applications,
                            isLoading = false
                        )
                    }
                }
        }
    }
}