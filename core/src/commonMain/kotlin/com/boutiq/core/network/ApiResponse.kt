package com.boutiq.core.network

import kotlinx.serialization.Serializable

/**
 * Common response structure for all API calls.
 * @param success Indicates if the API call was successful
 * @param statusCode HTTP status code
 * @param message Optional message, typically used for error descriptions
 * @param data Optional data returned by the API
 */
@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val statusCode: Int,
    val message: String? = null,
    val data: T? = null
)
