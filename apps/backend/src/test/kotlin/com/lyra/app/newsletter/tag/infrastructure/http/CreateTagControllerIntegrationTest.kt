package com.lyra.app.newsletter.tag.infrastructure.http

import com.lyra.ControllerIntegrationTest
import com.lyra.app.newsletter.tag.TagStub
import java.util.UUID
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.context.jdbc.Sql

internal class CreateTagControllerIntegrationTest : ControllerIntegrationTest() {
    private val tagId = UUID.randomUUID()
    private val organizationId = "a0654720-35dc-49d0-b508-1f7df5d915f1"
    private val url = "/api/organization/$organizationId/tag/$tagId"

    @Sql(
        "/db/organization/organization.sql",
    )
    @Sql(
        "/db/organization/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    @Test
    fun `should create a new tag`() {
        val request = TagStub.generateRequest()
        webTestClient.mutateWith(csrf()).put()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody().isEmpty
    }

    @Sql(
        "/db/organization/organization.sql",
    )
    @Sql(
        "/db/organization/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    @Test
    fun `should create a new tag with default color`() {
        webTestClient.mutateWith(csrf()).put()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("""{"name": "Test Tag"}""")
            .exchange()
            .expectStatus().isCreated
            .expectBody().isEmpty
    }
    @Sql(
        "/db/organization/organization.sql",
    )
    @Sql(
        "/db/organization/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    @Test
    fun `should not create a new tag if name is empty`() {
        val request = TagStub.generateRequest(name = "")
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
            .consumeWith(::println)
    }
}
