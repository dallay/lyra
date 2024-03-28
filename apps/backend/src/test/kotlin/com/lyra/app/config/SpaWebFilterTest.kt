package com.lyra.app.config

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.mock.http.server.reactive.MockServerHttpRequest
import org.springframework.mock.web.server.MockServerWebExchange
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class SpaWebFilterTest {

    private val webFilterChain: WebFilterChain = mock(WebFilterChain::class.java)
    private val spaWebFilter = SpaWebFilter()

    @Test
    fun `redirects when path does not start with excluded prefixes and does not contain dot`() {
        val request = MockServerHttpRequest.get("/somepath")
        val exchange = MockServerWebExchange.from(request)

        `when`(webFilterChain.filter(any())).thenReturn(Mono.empty())

        StepVerifier.create(spaWebFilter.filter(exchange, webFilterChain))
            .expectComplete()
            .verify()

        assertEquals("/somepath", exchange.request.uri.path)
    }

    @Test
    fun `does not redirect when path starts with excluded prefix`() {
        val request = MockServerHttpRequest.get("/api/somepath")
        val exchange = MockServerWebExchange.from(request)

        `when`(webFilterChain.filter(any())).thenReturn(Mono.empty())

        StepVerifier.create(spaWebFilter.filter(exchange, webFilterChain))
            .expectComplete()
            .verify()

        assertEquals("/api/somepath", exchange.request.uri.path)
    }

    @Test
    fun `does not redirect when path contains dot`() {
        val request = MockServerHttpRequest.get("/somepath.js")
        val exchange = MockServerWebExchange.from(request)

        `when`(webFilterChain.filter(any())).thenReturn(Mono.empty())

        StepVerifier.create(spaWebFilter.filter(exchange, webFilterChain))
            .expectComplete()
            .verify()

        assertEquals("/somepath.js", exchange.request.uri.path)
    }
}
