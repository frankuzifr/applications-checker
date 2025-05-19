package com.frankuzi.applicationschecker.presentation.applications_list

import com.frankuzi.applicationschecker.domain.model.ApplicationInfo

sealed interface ApplicationsListEvent {
    class ApplicationClickEvent(val applicationInfo: ApplicationInfo): ApplicationsListEvent
}