package com.githubapiconsumer.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class HttpClientConfiguration(@Value("\${github.baseUrl}") private val baseUrl: String) {
    @Bean
    fun webClient(): WebClient = WebClient.builder().baseUrl(baseUrl).build()
}


