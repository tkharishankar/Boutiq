package com.boutiq.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boutiq.core.session.SessionManager
import com.boutiq.feature.auth.api.AuthApiService
import com.boutiq.feature.auth.model.CustomerState
import com.boutiq.feature.auth.model.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authApiService: AuthApiService,
    private val sessionManager: SessionManager,
) : ViewModel() {

    data class AuthUiState(
        val isLoading: Boolean = false,
        val phoneNumber: String = "",
        val password: String = "",
        val firstName: String = "",
        val lastName: String = "",
        val email: String = "",
        val errorMessage: String? = null,
        val loginResponse: LoginResponse? = null,
        val isRegistrationSuccessful: Boolean = false,

        // Forgot Password Flow States
        val currentScreen: AuthScreen = AuthScreen.LOGIN,
        val otpId: String = "",
        val otpCode: String = "",
        val newPassword: String = "",
        val confirmPassword: String = "",
        val verificationToken: String = "",
        val isOtpSent: Boolean = false,
        val isOtpVerified: Boolean = false,
        val isPasswordChanged: Boolean = false,
        val successMessage: String? = null,
    )

    enum class AuthScreen {
        LOGIN,
        REGISTER,
        FORGOT_PASSWORD,
        OTP_VERIFICATION,
        CHANGE_PASSWORD,
        DASHBOARD
    }

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun updatePhoneNumber(phoneNumber: String) {
        _uiState.value = _uiState.value.copy(phoneNumber = phoneNumber)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun updateFirstName(firstName: String) {
        _uiState.value = _uiState.value.copy(firstName = firstName)
    }

    fun updateLastName(lastName: String) {
        _uiState.value = _uiState.value.copy(lastName = lastName)
    }

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun login() {
        val currentState = _uiState.value
        if (currentState.phoneNumber.isBlank() || currentState.password.isBlank()) {
            _uiState.value =
                currentState.copy(errorMessage = "Phone number and password are required")
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true, errorMessage = null)

            authApiService.login(currentState.phoneNumber, currentState.password)
                .onSuccess { response ->
                    // Save login session
                    sessionManager.saveSession(
                        userId = response.customerId,
                        userName = response.name,
                        authToken = response.accessToken
                    )

                    _uiState.value = currentState.copy(
                        isLoading = false,
                        loginResponse = response
                    )
                }
                .onFailure { error ->
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Unknown error"
                    )
                }
        }
    }

    fun register() {
        val currentState = _uiState.value
        if (currentState.firstName.isBlank() || currentState.lastName.isBlank() ||
            currentState.phoneNumber.isBlank() || currentState.password.isBlank()
        ) {
            _uiState.value =
                currentState.copy(errorMessage = "First name, last name, phone number, and password are required")
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true, errorMessage = null)
            authApiService.register(
                firstName = currentState.firstName,
                lastName = currentState.lastName,
                phoneNumber = currentState.phoneNumber,
                password = currentState.password,
                email = currentState.email.ifBlank { null }
            )
                .onSuccess { response ->
                    // Save login session
                    sessionManager.saveSession(
                        userId = response.customerId,
                        userName = response.name,
                        authToken = response.accessToken
                    )

                    _uiState.value = currentState.copy(
                        isLoading = false,
                        loginResponse = response,
                        currentScreen = when (response.state) {
                            CustomerState.REGISTER -> AuthScreen.OTP_VERIFICATION
                            else -> AuthScreen.DASHBOARD
                        },
                        isRegistrationSuccessful = true
                    )
                }
                .onFailure { error ->
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Unknown error"
                    )
                }
        }
    }

    // Navigation methods
    fun navigateToLogin() {
        _uiState.value = _uiState.value.copy(
            currentScreen = AuthScreen.LOGIN,
            errorMessage = null,
            successMessage = null
        )
    }

    fun navigateToRegister() {
        _uiState.value = _uiState.value.copy(
            currentScreen = AuthScreen.REGISTER,
            errorMessage = null,
            successMessage = null
        )
    }

    fun navigateToForgotPassword() {
        _uiState.value = _uiState.value.copy(
            currentScreen = AuthScreen.FORGOT_PASSWORD,
            errorMessage = null,
            successMessage = null,
            isOtpSent = false,
            isOtpVerified = false,
            isPasswordChanged = false,
            otpId = "",
            otpCode = "",
            verificationToken = "",
            newPassword = "",
            confirmPassword = ""
        )
    }

    fun navigateToOtpVerification() {
        _uiState.value = _uiState.value.copy(
            currentScreen = AuthScreen.OTP_VERIFICATION,
            errorMessage = null,
            successMessage = null,
            otpCode = ""
        )
    }

    // Forgot Password Flow methods
    fun updateOtpCode(code: String) {
        _uiState.value = _uiState.value.copy(otpCode = code)
    }

    fun updateNewPassword(password: String) {
        _uiState.value = _uiState.value.copy(newPassword = password)
    }

    fun updateConfirmPassword(password: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = password)
    }

    fun sendOtp() {
        val currentState = _uiState.value
        if (currentState.phoneNumber.isBlank()) {
            _uiState.value = currentState.copy(errorMessage = "Phone number is required")
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true, errorMessage = null)
            authApiService.sendOtpByPhoneNumber(currentState.phoneNumber)
                .onSuccess { response ->
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        otpId = response.verificationId,
                        isOtpSent = true,
                        currentScreen = AuthScreen.OTP_VERIFICATION,
                        errorMessage = null,
                        successMessage = "OTP sent to your phone number"
                    )
                }
                .onFailure { error ->
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Failed to send OTP"
                    )
                }
        }
    }

    fun verifyOtp() {
        val currentState = _uiState.value
        if (currentState.otpCode.isBlank()) {
            _uiState.value = currentState.copy(errorMessage = "OTP code is required")
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true, errorMessage = null)

            authApiService.verifyOtpByPhoneNumber(
                currentState.phoneNumber,
                currentState.otpId,
                currentState.otpCode
            )
                .onSuccess { response ->
                    if (response.errorMessage == null) {
                        _uiState.value = currentState.copy(
                            isLoading = false,
                            isOtpVerified = true,
                            currentScreen = AuthScreen.CHANGE_PASSWORD,
                            successMessage = "OTP verified successfully"
                        )
                    } else {
                        _uiState.value = currentState.copy(
                            isLoading = false,
                            errorMessage = "Invalid OTP"
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Failed to verify OTP"
                    )
                }
        }
    }

    fun changePassword() {
        val currentState = _uiState.value
        if (currentState.newPassword.isBlank()) {
            _uiState.value = currentState.copy(errorMessage = "New password is required")
            return
        }

        if (currentState.newPassword != currentState.confirmPassword) {
            _uiState.value = currentState.copy(errorMessage = "Passwords do not match")
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true, errorMessage = null)

            authApiService.changePassword(
                currentState.phoneNumber,
                currentState.newPassword,
                currentState.verificationToken
            )
                .onSuccess { response ->
                    if (response.success) {
                        _uiState.value = currentState.copy(
                            isLoading = false,
                            isPasswordChanged = true,
                            currentScreen = AuthScreen.LOGIN,
                            successMessage = "Password changed successfully. Please login with your new password.",
                            newPassword = "",
                            confirmPassword = "",
                            verificationToken = ""
                        )
                    } else {
                        _uiState.value = currentState.copy(
                            isLoading = false,
                            errorMessage = response.message
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Failed to change password"
                    )
                }
        }
    }

    // Clear login response to prevent multiple navigations
    fun clearLoginResponse() {
        _uiState.value = _uiState.value.copy(loginResponse = null)
    }
}
