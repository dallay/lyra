package com.lyra.app.authentication.infrastructure

import com.lyra.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@IntegrationTest
@AutoConfigureWebTestClient
class AuthenticationExceptionAdviceIT {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun shouldHandleNotAuthenticatedUserException() {
        webTestClient.get().uri("/api/account-exceptions/not-authenticated")
            .exchange()
            .expectStatus().isUnauthorized
            .expectHeader().contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .expectBody()
            .jsonPath("$.message").isEqualTo("error.http.401")
            .jsonPath("$.title").isEqualTo("not authenticated")
    }

    @Test
    fun shouldHandleUnknownAuthenticationException() {
        webTestClient.get().uri("/api/account-exceptions/unknown-authentication")
            .exchange()
            .expectStatus().is5xxServerError
            .expectHeader().contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .expectBody()
            .jsonPath("$.message").isEqualTo("error.http.500")
            .jsonPath("$.title").isEqualTo("unknown authentication")
    }
}
