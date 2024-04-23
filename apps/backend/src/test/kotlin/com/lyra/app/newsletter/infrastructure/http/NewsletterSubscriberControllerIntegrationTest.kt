package com.lyra.app.newsletter.infrastructure.http

import com.lyra.app.ControllerIntegrationTest
import com.lyra.app.IntegrationTest
import com.lyra.app.newsletter.SubscriberStub.generateRequest
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType

private const val ENDPOINT = "/api/newsletter/subscribers"

@IntegrationTest
internal class NewsletterSubscriberControllerIntegrationTest : ControllerIntegrationTest() {

    @BeforeEach
    fun setUp() {
        // Set Test container here
    }

    @Test
    fun `should subscribe a new subscriber`() {
        val request = generateRequest()
        webTestClient.put()
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
        webTestClient.put()
            .uri("$ENDPOINT/${UUID.randomUUID()}")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                request,
            )
            .exchange()
            .expectStatus().isBadRequest
            .expectBody().jsonPath("$.type")
            .isEqualTo("https://lyra.io/errors/bad-request")
            .jsonPath("$.title")
            .isEqualTo("Bad request")
            .jsonPath("$.status")
            .isEqualTo(400)
            .jsonPath("$.detail")
            .isEqualTo("The email <invalid-email> is not valid")
            .jsonPath("$.instance").isNotEmpty
    }
}
