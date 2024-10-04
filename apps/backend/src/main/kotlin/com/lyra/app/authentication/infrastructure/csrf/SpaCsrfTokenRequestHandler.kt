package com.lyra.app.authentication.infrastructure.csrf

import org.slf4j.LoggerFactory
import org.springframework.security.web.server.csrf.CsrfToken
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestAttributeHandler
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestHandler
import org.springframework.security.web.server.csrf.XorServerCsrfTokenRequestAttributeHandler
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class SpaCsrfTokenRequestHandler(
    private val delegate: ServerCsrfTokenRequestHandler = XorServerCsrfTokenRequestAttributeHandler()
) : ServerCsrfTokenRequestAttributeHandler() {

    override fun handle(exchange: ServerWebExchange, csrfToken: Mono<CsrfToken>) {
        log.debug(
            "Handling CsrfToken request: {} {}",
            exchange,
            csrfToken,
        )
        /*
         * Always use XorCsrfTokenRequestAttributeHandler to provide BREACH protection of
         * the CsrfToken when it is rendered in the response body.
         */
        delegate.handle(exchange, csrfToken)
    }

    override fun resolveCsrfTokenValue(exchange: ServerWebExchange, csrfToken: CsrfToken): Mono<String> {
        log.debug("Resolving CsrfToken value: {} {}", exchange, csrfToken)
        /*
         * If the request contains a request header, use CsrfTokenRequestAttributeHandler
         * to resolve the CsrfToken. This applies when a single-page application includes
         * the header value automatically, which was obtained via a cookie containing the
         * raw CsrfToken.
         */
        return if (exchange.request.headers.getFirst(csrfToken.headerName) != null) {
            super.resolveCsrfTokenValue(exchange, csrfToken)
        } else {
            /*
             * In all other cases (e.g. if the request contains a request parameter), use
             * XorCsrfTokenRequestAttributeHandler to resolve the CsrfToken. This applies
             * when a server-side rendered form includes the _csrf request parameter as a
             * hidden input.
             */
            delegate.resolveCsrfTokenValue(exchange, csrfToken)
        }
    }
    companion object {
        private val log = LoggerFactory.getLogger(SpaCsrfTokenRequestHandler::class.java)
    }
}
