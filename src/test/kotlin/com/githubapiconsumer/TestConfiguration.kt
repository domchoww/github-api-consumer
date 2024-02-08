package com.githubapiconsumer

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Primary
import org.springframework.web.reactive.function.client.WebClient

@TestConfiguration
class ClientConfiguration {
    @Bean
    @Primary
    fun testWebClient(): WebClient {
        return WebClient.builder().baseUrl("http://localhost:8081").build()
    }
}

