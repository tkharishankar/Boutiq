package com.boutiq.feature.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.boutiq.feature.auth.model.CustomerState
import com.boutiq.feature.auth.viewmodel.AuthViewModel
import org.koin.compose.koinInject

@Composable
fun AuthContent(
    onLoginSuccess: () -> Unit,
    onNavigateToDashboard: () -> Unit,
) {
    val viewModel: AuthViewModel = koinInject()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.currentScreen) {
        if (uiState.currentScreen == AuthViewModel.AuthScreen.DASHBOARD) {
            viewModel.clearLoginResponse()
            onNavigateToDashboard()
        }
    }

    when (uiState.currentScreen) {
        AuthViewModel.AuthScreen.LOGIN -> {
            AuthScreen(
                onLoginSuccess = { /* Success handled by CustomerState check above */ },
                onNavigateToRegister = { viewModel.navigateToRegister() },
                onNavigateToForgotPassword = { viewModel.navigateToForgotPassword() }
            )
        }

        AuthViewModel.AuthScreen.REGISTER -> {
            RegisterScreen(
                onRegistrationSuccess = { /* Success handled by CustomerState check above */ },
                onNavigateToLogin = { viewModel.navigateToLogin() }
            )
        }

        AuthViewModel.AuthScreen.FORGOT_PASSWORD -> {
            ForgotPasswordScreen(
                onNavigateToLogin = { viewModel.navigateToLogin() }
            )
        }

        AuthViewModel.AuthScreen.OTP_VERIFICATION -> {
            OtpVerificationScreen(
                onNavigateToForgotPassword = { viewModel.navigateToForgotPassword() },
                onVerificationSuccess = onNavigateToDashboard
            )
        }

        AuthViewModel.AuthScreen.CHANGE_PASSWORD -> {
            ChangePasswordScreen(
                onNavigateToOtpVerification = { viewModel.navigateToOtpVerification() }
            )
        }

        AuthViewModel.AuthScreen.DASHBOARD -> {
            /* Success handled by CustomerState check above */
        }
    }
}
