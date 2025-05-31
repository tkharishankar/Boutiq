package com.boutiq.core.network

interface NetworkClient {
    suspend fun <T> get(url: String, headers: Map<String, String> = emptyMap()): ApiResponse<T>

    suspend fun <T> post(url: String, body: Any, headers: Map<String, String> = emptyMap()): ApiResponse<T>

    suspend fun <T> put(url: String, body: Any, headers: Map<String, String> = emptyMap()): ApiResponse<T>

    suspend fun <T> delete(url: String, headers: Map<String, String> = emptyMap()): ApiResponse<T>
}
