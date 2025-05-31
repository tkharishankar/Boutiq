package com.boutiq.feature.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import com.boutiq.core.session.SessionManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    data class DashboardUiState(
        val isLoggedIn: Boolean = true,
        val userName: String = ""
    )

    private val _uiState = MutableStateFlow(DashboardUiState(
        isLoggedIn = sessionManager.isLoggedIn(),
        userName = sessionManager.getUserName()
    ))
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    // Event to notify when logout is complete
    private val _logoutEvent = MutableSharedFlow<Unit>()
    val logoutEvent: SharedFlow<Unit> = _logoutEvent.asSharedFlow()

    /**
     * Logs out the user by clearing the session data and emitting a logout event
     */
    suspend fun logout() {
        sessionManager.clearSession()
        _uiState.value = _uiState.value.copy(isLoggedIn = false)
        _logoutEvent.emit(Unit)
    }
}
