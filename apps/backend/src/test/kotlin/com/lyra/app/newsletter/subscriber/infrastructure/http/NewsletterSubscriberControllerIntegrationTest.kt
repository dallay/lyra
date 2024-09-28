package com.lyra.app.newsletter.subscriber.infrastructure.http

import com.lyra.ControllerIntegrationTest
import com.lyra.app.newsletter.subscriber.SubscriberStub.generateRequest
import java.util.*
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.context.jdbc.Sql

internal class NewsletterSubscriberControllerIntegrationTest : ControllerIntegrationTest() {
    private val organizationId = "a0654720-35dc-49d0-b508-1f7df5d915f1"
    private val url = "/api/organization/$organizationId/newsletter/subscriber/${UUID.randomUUID()}"

    @Sql(
        "/db/organization/organization.sql",
    )
    @Sql(
        "/db/organization/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    @Test
    fun `should subscribe a new subscriber`() {
        val request = generateRequest()
        webTestClient.mutateWith(csrf()).put()
            .uri(url)
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
            .uri(url)
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
