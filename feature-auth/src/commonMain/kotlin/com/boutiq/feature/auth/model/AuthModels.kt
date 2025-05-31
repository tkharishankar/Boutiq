package com.boutiq.feature.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val phoneNumber: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val customerId: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val createdAt: Long,
    val updatedAt: Long,
    val state: CustomerState,
    val accessToken: String,
    val refreshToken: String
)

@Serializable
data class CustomerRegisterReq(
    val firstName: String = "",
    val lastName: String = "",
    val email: String? = null,
    val phoneNumber: String,
    val customerId: String? = null,
    val password: String,
)

// Forgot Password - Send OTP
@Serializable
data class SendOtpRequest(
    val phoneNumber: String
)

@Serializable
data class SendOtpResponse(
    val message: String,
    val otpId: String
)

// Verify OTP
@Serializable
data class VerifyOtpRequest(
    val phoneNumber: String,
    val otpId: String,
    val otpCode: String
)

@Serializable
data class VerifyOtpResponse(
    val message: String,
    val verified: Boolean,
    val token: String
)

// Change Password
@Serializable
data class ChangePasswordRequest(
    val phoneNumber: String,
    val newPassword: String,
    val token: String
)

@Serializable
data class ChangePasswordResponse(
    val message: String,
    val success: Boolean
)

enum class CustomerState {
    REGISTER,
    VERIFIED,
    GUEST
}

// Phone number OTP verification
@Serializable
data class OTPSentReq(
    val userId: String,
    val userType: String
)

@Serializable
data class OTPSentResp(
    val phoneNumber: String = "",
    val verificationId: String = " ",
    val responseCode: String = "",
    val verificationStatus: String? = null,
    val errorMessage: String? = null,
    val timeout: String? = null,
    val smsCLI: String? = null,
    val transactionId: String? = null,
)

@Serializable
data class OTPVerifyReq(
    val code: String,
    val verificationId: String,
    val userId: String,
    val userType: String
)
