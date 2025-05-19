package com.frankuzi.applicationschecker.presentation.application_detail

sealed interface ApplicationDetailEvent {
    object OnBackEvent: ApplicationDetailEvent
    object OpenClickEvent: ApplicationDetailEvent
}