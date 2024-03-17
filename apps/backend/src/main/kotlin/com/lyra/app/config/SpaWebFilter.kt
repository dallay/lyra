package com.lyra.app.config

import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class SpaWebFilter : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val path = exchange.request.uri.path
        if (!path.startsWith("/api") && path.matches("[^\\\\.]*".toRegex())) {
            val newPath = if (path.startsWith("/app")) "/app/index.html" else "/index.html"
            return chain.filter(
                exchange.mutate().request(
                    exchange.request.mutate().path(newPath).build(),
                ).build(),
            )
        }
        return chain.filter(exchange)
    }
}
