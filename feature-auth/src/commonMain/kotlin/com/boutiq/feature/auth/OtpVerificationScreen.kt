package com.boutiq.feature.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.boutiq.core.ui.BoutiqButton
import com.boutiq.core.ui.BoutiqOutlinedTextField
import com.boutiq.core.ui.BoutiqTextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.boutiq.feature.auth.viewmodel.AuthViewModel
import org.koin.compose.koinInject

@Composable
fun OtpVerificationScreen(
    onNavigateToForgotPassword: () -> Unit,
    onVerificationSuccess: () -> Unit = {}
) {
    val viewModel: AuthViewModel = koinInject()
    val uiState by viewModel.uiState.collectAsState()

    // Check if verification was successful and navigate accordingly
    LaunchedEffect(uiState.isOtpVerified) {
        if (uiState.isOtpVerified) {
            onVerificationSuccess()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Verify OTP",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(
                text = "Enter the verification code sent to ${uiState.phoneNumber}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            BoutiqOutlinedTextField(
                value = uiState.otpCode,
                onValueChange = { viewModel.updateOtpCode(it) },
                label = "Verification Code",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            if (uiState.successMessage != null) {
                Text(
                    text = uiState.successMessage!!,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            BoutiqButton(
                onClick = { viewModel.verifyOtp() },
                modifier = Modifier.fillMaxWidth(),
                isLoading = uiState.isLoading,
                text = "Verify Code"
            )

            Spacer(modifier = Modifier.height(16.dp))

            BoutiqTextButton(
                onClick = { viewModel.sendOtp() },
                text = "Resend Code"
            )

            Spacer(modifier = Modifier.height(8.dp))

            BoutiqTextButton(
                onClick = onNavigateToForgotPassword,
                text = "Back"
            )
        }
    }
}
