package com.boutiq

import com.boutiq.core.network.KtorHttpClient
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ComposeAppCommonTest {

    @Test
    fun example() {
        assertEquals(3, 1 + 2)
    }

    @Test
    fun testHttpLogging() = runBlocking {
        // This test will make a simple HTTP request to verify that logging works
        val client = KtorHttpClient()

        // Make a GET request to a public API
        val response = client.get<String>("https://jsonplaceholder.typicode.com/todos/1")

        // Verify that we got a response
        assertNotNull(response)
        // Verify that the response is successful
        assertEquals(true, response.success)
        // Verify that we have data
        assertNotNull(response.data)
        println("Test completed successfully. Check the logs for HTTP request/response details.")
    }
}
