package com.githubapiconsumer

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(ClientConfiguration::class)
abstract class IntegrationTest {
    lateinit var mockServer: MockWebServer

    @Autowired
    protected lateinit var webTestClient: WebTestClient

    @BeforeEach
    protected fun setup() {
        mockServer = MockWebServer().also { it.start(8081) }
    }

    @AfterEach
    protected fun tearDown() {
        mockServer.shutdown()
    }

    protected fun MockWebServer.enqueueJsonResponse(code: Int, response: String) {
        this.enqueue(
            MockResponse()
                .setResponseCode(code)
                .addHeader("Content-Type", "application/json")
                .setBody(response)
        )
    }
}