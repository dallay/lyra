package com.lyra.app.authentication.infrastructure.csrf

import com.lyra.UnitTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.web.server.csrf.CsrfToken
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestHandler
import org.springframework.util.MultiValueMap
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@UnitTest
internal class SpaCsrfTokenRequestHandlerTest {

    private lateinit var delegate: ServerCsrfTokenRequestHandler
    private lateinit var handler: SpaCsrfTokenRequestHandler
    private lateinit var exchange: ServerWebExchange
    private lateinit var csrfToken: CsrfToken

    @BeforeEach
    fun setup() {
        delegate = mockk()
        handler = SpaCsrfTokenRequestHandler(delegate)
        exchange = mockk()
        csrfToken = mockk()

        every { exchange.request.headers.getFirst(any()) } returns null
        every { exchange.formData } returns Mono.empty<MultiValueMap<String, String>>()
        every { delegate.handle(any(), any()) } returns Unit
        every { delegate.resolveCsrfTokenValue(any(), any()) } returns Mono.just("token-value")
    }

    @Test
    fun `should use delegate handle method`() {
        val csrfTokenMono = Mono.just(csrfToken)

        handler.handle(exchange, csrfTokenMono)

        verify { delegate.handle(exchange, csrfTokenMono) }
    }

    @Test
    fun `should resolve CsrfToken value from header`() {
        every { csrfToken.headerName } returns "X-CSRF-TOKEN"
        every { exchange.request.headers.getFirst("X-CSRF-TOKEN") } returns "header-token"

        val result = handler.resolveCsrfTokenValue(exchange, csrfToken).block()

        assertEquals("header-token", result)
        verify { exchange.request.headers.getFirst("X-CSRF-TOKEN") }
    }

    @Test
    fun `should delegate resolveCsrfTokenValue when header is not present`() {
        every { csrfToken.headerName } returns "X-CSRF-TOKEN"

        val result = handler.resolveCsrfTokenValue(exchange, csrfToken).block()

        assertEquals("token-value", result)
        verify { delegate.resolveCsrfTokenValue(exchange, csrfToken) }
    }
}
