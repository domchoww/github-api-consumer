package com.githubapiconsumer.configuration

import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono


@Component
@Order(-1)
class RequestHeaderModifierFilter : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val headers = HttpHeaders()
        headers.putAll(exchange.request.headers)
        headers["Accept"] = "application/json"
        val modifiedExchange = exchange.mutate().request { mutatedRequest: ServerHttpRequest.Builder ->
            mutatedRequest.headers { httpHeaders: HttpHeaders ->
                httpHeaders.addAll(
                    headers
                )
            }
        }.build()
        return chain.filter(modifiedExchange)
    }
}