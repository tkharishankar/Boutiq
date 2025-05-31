package com.boutiq.core.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.errors.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive

class KtorHttpClient : NetworkClient {
    private val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(json)
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println("HTTP Client: $message")
                }
            }
            level = LogLevel.ALL
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 15000
            socketTimeoutMillis = 15000
        }
    }

    override suspend fun <T> get(url: String, headers: Map<String, String>): ApiResponse<T> {
        return try {
            val response = client.get(url) {
                headers.forEach { (key, value) ->
                    header(key, value)
                }
            }
            handleResponse(response)
        } catch (e: Exception) {
            handleException<T>(e)
        }
    }

    override suspend fun <T> post(url: String, body: Any, headers: Map<String, String>): ApiResponse<T> {
        return try {
            val response = client.post(url) {
                headers.forEach { (key, value) ->
                    header(key, value)
                }
                setBody(body)
            }
            handleResponse(response)
        } catch (e: Exception) {
            handleException<T>(e)
        }
    }

    override suspend fun <T> put(url: String, body: Any, headers: Map<String, String>): ApiResponse<T> {
        return try {
            val response = client.put(url) {
                headers.forEach { (key, value) ->
                    header(key, value)
                }
                setBody(body)
            }
            handleResponse(response)
        } catch (e: Exception) {
            handleException<T>(e)
        }
    }

    override suspend fun <T> delete(url: String, headers: Map<String, String>): ApiResponse<T> {
        return try {
            val response = client.delete(url) {
                headers.forEach { (key, value) ->
                    header(key, value)
                }
            }
            handleResponse(response)
        } catch (e: Exception) {
            handleException<T>(e)
        }
    }

    private suspend fun <T> handleResponse(response: HttpResponse): ApiResponse<T> {
        val statusCode = response.status.value
        val responseBody = response.bodyAsText()

        return try {
            // First, try to parse as JSON to check for 'success' field
            val jsonObject = json.decodeFromString<JsonObject>(responseBody)

            // Check if response has 'success' field
            if (jsonObject.containsKey("success")) {
                val success = jsonObject["success"]?.jsonPrimitive?.boolean ?: false
                val apiStatusCode = jsonObject["statusCode"]?.jsonPrimitive?.int ?: statusCode
                val message = jsonObject["message"]?.jsonPrimitive?.content

                if (!success) {
                    // API indicates failure, return error with message
                    return ApiResponse<T>(
                        success = false,
                        statusCode = apiStatusCode,
                        message = message ?: "Operation failed"
                    )
                } else {
                    // API indicates success
                    @Suppress("UNCHECKED_CAST")
                    return ApiResponse(
                        success = true,
                        statusCode = apiStatusCode,
                        message = message,
                        data = responseBody as T
                    )
                }
            } else {
                // No 'success' field, treat based on HTTP status
                if (response.status.isSuccess()) {
                    @Suppress("UNCHECKED_CAST")
                    ApiResponse(
                        success = true,
                        statusCode = statusCode,
                        data = responseBody as T
                    )
                } else {
                    // Try to extract message for error display
                    val message = jsonObject["message"]?.jsonPrimitive?.content ?: "Unknown error"
                    ApiResponse<T>(
                        success = false,
                        statusCode = statusCode,
                        message = message
                    )
                }
            }
        } catch (e: Exception) {
            // Fallback error handling
            if (response.status.isSuccess()) {
                @Suppress("UNCHECKED_CAST")
                ApiResponse(
                    success = true,
                    statusCode = statusCode,
                    data = responseBody as T
                )
            } else {
                ApiResponse<T>(
                    success = false,
                    statusCode = statusCode,
                    message = responseBody.takeIf { it.isNotBlank() } ?: "An error occurred"
                )
            }
        }
    }

    private fun <T> handleException(e: Exception): ApiResponse<T> {
        return when (e) {
            is IOException -> ApiResponse(
                success = false,
                statusCode = 503,
                message = "Network error: ${e.message}"
            )
            is HttpRequestTimeoutException -> ApiResponse(
                success = false,
                statusCode = 408,
                message = "Request timeout: ${e.message}"
            )
            else -> ApiResponse(
                success = false,
                statusCode = 500,
                message = "Unknown error: ${e.message}"
            )
        }
    }
}
