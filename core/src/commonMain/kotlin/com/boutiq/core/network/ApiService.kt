package com.boutiq.core.network

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ApiService(private val networkClient: NetworkClient) {

    private val baseUrl = ""
    private val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    suspend fun <T> fetchData(endpoint: String): ApiResponse<T> {
        return networkClient.get("$baseUrl/$endpoint")
    }

    suspend fun <T> sendData(endpoint: String, data: Any): ApiResponse<T> {
        return networkClient.post("$baseUrl/$endpoint", data)
    }

    suspend fun <T> updateData(endpoint: String, data: Any): ApiResponse<T> {
        return networkClient.put("$baseUrl/$endpoint", data)
    }

    suspend fun <T> deleteData(endpoint: String): ApiResponse<T> {
        return networkClient.delete("$baseUrl/$endpoint")
    }

    // Helper function to parse response data
    fun <T> parseData(response: ApiResponse<String>, parser: (String) -> T?): T? {
        return if (response.success && response.data != null) {
            try {
                parser(response.data)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    // Convenience method for parsing JSON data
    fun <T> parseJsonData(response: ApiResponse<String>, parser: (String) -> T?): T? {
        return parseData(response, parser)
    }

    // Helper function to handle API errors
    fun <T> handleApiError(response: ApiResponse<T>): String {
        return if (!response.success) {
            response.message ?: "Unknown error occurred"
        } else {
            "No error"
        }
    }
}
