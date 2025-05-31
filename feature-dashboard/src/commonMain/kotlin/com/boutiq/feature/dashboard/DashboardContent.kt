package com.boutiq.feature.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.boutiq.core.Greeting
import com.boutiq.core.session.SessionManager
import com.boutiq.feature.dashboard.viewmodel.DashboardViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private class DashboardDependencies : KoinComponent {
    val greeting: Greeting by inject()
    val sessionManager: SessionManager by inject()
}

@Composable
fun DashboardContent(onLogout: () -> Unit = {}) {
    val dependencies = remember { DashboardDependencies() }
    val greeting = dependencies.greeting.greet()
    val coroutineScope = rememberCoroutineScope()

    val viewModel = remember { DashboardViewModel(dependencies.sessionManager) }
    val uiState by viewModel.uiState.collectAsState()

    // Observe logout events
    LaunchedEffect(viewModel) {
        viewModel.logoutEvent.collectLatest {
            onLogout()
        }
    }

    DashboardScreen(
        greeting = greeting,
        userName = uiState.userName,
        onLogout = { 
            coroutineScope.launch {
                viewModel.logout()
            }
        }
    )
}
