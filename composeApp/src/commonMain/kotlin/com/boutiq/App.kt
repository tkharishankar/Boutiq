package com.boutiq

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.boutiq.core.session.SessionManager
import com.boutiq.feature.auth.AuthContent
import com.boutiq.feature.dashboard.DashboardContent
import com.boutiq.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    AppTheme {
        val sessionManager = koinInject<SessionManager>()
        var isLoggedIn by remember { mutableStateOf(sessionManager.isLoggedIn()) }

        if (isLoggedIn) {
            DashboardContent(
                onLogout = {
                    // Update the login state to trigger navigation to the auth screen
                    isLoggedIn = false
                }
            )
        } else {
            AuthContent(
                onLoginSuccess = { isLoggedIn = true },
                onNavigateToDashboard = { isLoggedIn = true },
            )
        }
    }
}
