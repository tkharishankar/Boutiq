package com.boutiq.feature.auth.api

import com.boutiq.core.network.ApiResponse
import com.boutiq.core.network.ApiService
import com.boutiq.feature.auth.model.*
import kotlinx.serialization.json.Json

class AuthApiService(private val apiService: ApiService) {

    private val loginEndpoint = "api/v1/customers/login"
    private val registerEndpoint = "api/v1/customers/register"
    private val sendOtpEndpoint = "api/v1/customers/send-otp"
    private val verifyOtpEndpoint = "api/v1/customers/verify-otp"
    private val changePasswordEndpoint = "api/v1/customers/change-password"
    private val sendOtpByPhoneNumberEndpoint = "api/v1/otp/sendByPhoneNumber"
    private val validateOtpByPhoneNumberEndpoint = "api/v1/otp/validateByPhoneNumber"

    private val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    suspend fun login(phoneNumber: String, password: String): Result<LoginResponse> {
        return try {
            val request = LoginRequest(phoneNumber, password)
            val response: ApiResponse<String> = apiService.sendData(loginEndpoint, request)

            if (!response.success) {
                return Result.failure(Exception(response.message ?: "Login failed"))
            }

            val responseData = response.data ?: return Result.failure(Exception("No data in response"))
            val loginResponse = json.decodeFromString<LoginResponse>(responseData)
            Result.success(loginResponse)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        password: String,
        email: String? = null,
        customerId: String? = null
    ): Result<LoginResponse> {
        return try {
            val request = CustomerRegisterReq(
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber,
                password = password,
                email = email,
                customerId = customerId
            )
            val response: ApiResponse<String> = apiService.sendData(registerEndpoint, request)

            if (!response.success) {
                return Result.failure(Exception(response.message ?: "Registration failed"))
            }

            val responseData = response.data ?: return Result.failure(Exception("No data in response"))
            val registerResponse = json.decodeFromString<LoginResponse>(responseData)
            Result.success(registerResponse)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendOtp(phoneNumber: String): Result<SendOtpResponse> {
        return try {
            val request = SendOtpRequest(phoneNumber)
            val response: ApiResponse<String> = apiService.sendData(sendOtpEndpoint, request)

            if (!response.success) {
                return Result.failure(Exception(response.message ?: "Failed to send OTP"))
            }

            val responseData = response.data ?: return Result.failure(Exception("No data in response"))
            val sendOtpResponse = json.decodeFromString<SendOtpResponse>(responseData)
            Result.success(sendOtpResponse)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun verifyOtp(phoneNumber: String, otpId: String, otpCode: String): Result<VerifyOtpResponse> {
        return try {
            val request = VerifyOtpRequest(phoneNumber, otpId, otpCode)
            val response: ApiResponse<String> = apiService.sendData(verifyOtpEndpoint, request)

            if (!response.success) {
                return Result.failure(Exception(response.message ?: "OTP verification failed"))
            }

            val responseData = response.data ?: return Result.failure(Exception("No data in response"))
            val verifyOtpResponse = json.decodeFromString<VerifyOtpResponse>(responseData)
            Result.success(verifyOtpResponse)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun changePassword(phoneNumber: String, newPassword: String, token: String): Result<ChangePasswordResponse> {
        return try {
            val request = ChangePasswordRequest(phoneNumber, newPassword, token)
            val response: ApiResponse<String> = apiService.sendData(changePasswordEndpoint, request)

            if (!response.success) {
                return Result.failure(Exception(response.message ?: "Password change failed"))
            }

            val responseData = response.data ?: return Result.failure(Exception("No data in response"))
            val changePasswordResponse = json.decodeFromString<ChangePasswordResponse>(responseData)
            Result.success(changePasswordResponse)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendOtpByPhoneNumber(phoneNumber: String): Result<OTPSentResp> {
        return try {
            val request = OTPSentReq(
                userId = phoneNumber,
                userType = "CUSTOMER"
            )
            val response: ApiResponse<String> = apiService.updateData(sendOtpByPhoneNumberEndpoint, request)

            if (!response.success) {
                return Result.failure(Exception(response.message ?: "Failed to send OTP"))
            }

            val responseData = response.data ?: return Result.failure(Exception("No data in response"))
            val otpSentResponse = json.decodeFromString<OTPSentResp>(responseData)
            Result.success(otpSentResponse)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun verifyOtpByPhoneNumber(otpValue: String, otpSentResp: OTPSentResp): Result<OTPSentResp> {
        return verifyOtpByPhoneNumber(
            otpValue = otpValue,
            verificationId = otpSentResp.verificationId,
            phoneNumber = otpSentResp.phoneNumber
        )
    }

    suspend fun verifyOtpByPhoneNumber(otpValue: String, verificationId: String, phoneNumber: String): Result<OTPSentResp> {
        val request = OTPVerifyReq(
            code = otpValue,
            verificationId = verificationId,
            userId = phoneNumber,
            userType = "CUSTOMER"
        )
        return verifyOtpByPhoneNumber(request)
    }

    suspend fun verifyOtpByPhoneNumber(req: OTPVerifyReq): Result<OTPSentResp> {
        return try {
            val response: ApiResponse<String> = apiService.updateData(validateOtpByPhoneNumberEndpoint, req)

            if (!response.success) {
                return Result.failure(Exception(response.message ?: "OTP verification failed"))
            }

            val responseData = response.data ?: return Result.failure(Exception("No data in response"))
            val verifyOtpResponse = json.decodeFromString<OTPSentResp>(responseData)
            Result.success(verifyOtpResponse)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
