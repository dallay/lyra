package com.lyra.app.authentication.infrastructure.filter

import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

/**
 * [SpaWebFilter] is a filter that forwards any unmapped paths (except those containing a period)
 * to the client `index.html`.
 */
class SpaWebFilter(
    private val excludedPrefixes: List<String> = listOf("/api", "/management", "/v3/api-docs", "/login", "/oauth2")
) : WebFilter {
    /**
     * Forwards any unmapped paths (except those containing a period) to the client `index.html`.
     */
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val path = exchange.request.uri.path
        return if (shouldRedirect(path)) {
            val modifiedRequest = exchange.request.mutate().path("/index.html").build()
            chain.filter(exchange.mutate().request(modifiedRequest).build())
        } else {
            chain.filter(exchange)
        }
    }

    private fun shouldRedirect(path: String): Boolean {
        return excludedPrefixes.none { path.startsWith(it) } &&
            !path.contains(".") // Always matches any path
    }
}
