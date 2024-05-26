package com.lyra.app.newsletter.infrastructure.http

import com.lyra.ControllerIntegrationTest
import com.lyra.app.newsletter.SubscriberStub.generateRequest
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf

private const val ENDPOINT = "/api/newsletter/subscribers"

internal class NewsletterSubscriberControllerIntegrationTest : ControllerIntegrationTest() {

    @BeforeEach
    fun setUp() {
        // Set Test container here
    }

    @Test
    fun `should subscribe a new subscriber`() {
        val request = generateRequest()
        webTestClient.mutateWith(csrf()).put()
            .uri("$ENDPOINT/${UUID.randomUUID()}")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody().isEmpty
    }

    @Test
    fun `should not subscribe a new subscriber if email is invalid`() {
        val request = generateRequest(email = "invalid-email")
        webTestClient.mutateWith(csrf()).put()
            .uri("$ENDPOINT/${UUID.randomUUID()}")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.title")
            .isEqualTo("Bad Request")
            .jsonPath("$.status")
            .isEqualTo(400)
            .jsonPath("$.instance").isNotEmpty
            .consumeWith { response ->
                println(response)
            }
    }
}
