package com.lyra.app.authentication.infrastructure.filter

import java.util.*
import org.springframework.http.ResponseCookie
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.web.server.csrf.CsrfToken
import org.springframework.util.StringUtils
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

/**
 *
 * @created 20/8/23
 */

class CookieCsrfFilter : WebFilter {
    /** {@inheritDoc}  */
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return if (exchange.request.cookies[CSRF_COOKIE_NAME] != null) {
            chain.filter(exchange)
        } else {
            Mono.just(exchange)
                .publishOn(Schedulers.boundedElastic())
                .flatMap { serverWebExchange: ServerWebExchange ->
                    serverWebExchange.getAttributeOrDefault(
                        CsrfToken::class.java.getName(),
                        Mono.empty<CsrfToken>(),
                    )
                }
                .doOnNext { token: CsrfToken ->
                    val cookie =
                        ResponseCookie.from(CSRF_COOKIE_NAME, token.token)
                            .maxAge(-1)
                            .httpOnly(false)
                            .path(getRequestContext(exchange.request))
                            .secure(Optional.ofNullable(exchange.request.sslInfo).isPresent)
                            .build()
                    exchange.response.cookies.add(CSRF_COOKIE_NAME, cookie)
                }
                .then(
                    Mono.defer {
                        chain.filter(
                            exchange,
                        )
                    },
                )
        }
    }

    private fun getRequestContext(request: ServerHttpRequest): String {
        val contextPath = request.path.contextPath().value()
        return if (StringUtils.hasLength(contextPath)) contextPath else "/"
    }

    companion object {
        private const val CSRF_COOKIE_NAME = "XSRF-TOKEN"
    }
}
