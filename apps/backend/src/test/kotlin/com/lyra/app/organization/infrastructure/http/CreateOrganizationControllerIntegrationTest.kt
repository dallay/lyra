package com.lyra.app.organization.infrastructure.http

import com.lyra.ControllerIntegrationTest
import com.lyra.app.organization.infrastructure.OrganizationStub
import com.lyra.app.organization.infrastructure.http.request.CreateOrganizationRequest
import java.util.*
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.test.context.jdbc.Sql

private const val ENDPOINT = "/api/organization"

internal class CreateOrganizationControllerIntegrationTest : ControllerIntegrationTest() {
    @Test
    fun `should create a new workspace`() {
        val request: CreateOrganizationRequest = OrganizationStub.generateRequest()
        val id = UUID.randomUUID().toString()
        webTestClient.mutateWith(csrf()).put()
            .uri("$ENDPOINT/$id")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody().isEmpty
    }

    @Test
    @Sql(
        "/db/organization/organization.sql",
    )
    @Sql(
        "/db/organization/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should fail when the workspace already exists`() {
        val request: CreateOrganizationRequest = OrganizationStub.generateRequest()
        val id = "a0654720-35dc-49d0-b508-1f7df5d915f1"
        webTestClient.mutateWith(csrf()).put()
            .uri("$ENDPOINT/$id")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.type").isEqualTo("https://lyra.com/errors/bad-request")
            .jsonPath("$.title").isEqualTo("Bad request")
            .jsonPath("$.status").isEqualTo(400)
            .jsonPath("$.detail").isEqualTo("Error creating organization")
            .jsonPath("$.instance").isEqualTo("$ENDPOINT/$id")
            .jsonPath("$.errorCategory").isEqualTo("BAD_REQUEST")
            .jsonPath("$.timestamp").isNumber
    }
}
