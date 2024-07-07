package com.lyra.app.organization.infrastructure.http

import com.lyra.ControllerIntegrationTest
import com.lyra.app.organization.OrganizationStub
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers
import org.springframework.test.context.jdbc.Sql

private const val ENDPOINT = "/api/organization/update"

internal class UpdateOrganizationControllerIntegrationTest : ControllerIntegrationTest() {

    @Test
    @Sql(
        "/db/organization/organization.sql",
    )
    @Sql(
        "/db/organization/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should update a new form`(): Unit = runBlocking {
        val id = "a0654720-35dc-49d0-b508-1f7df5d915f1"
        val request = OrganizationStub.generateUpdateRequest()
        webTestClient.mutateWith(SecurityMockServerConfigurers.csrf()).put()
            .uri("$ENDPOINT/$id")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .isEqualTo("Organization updated successfully")
    }

    @Test
    @Sql(
        "/db/organization/organization.sql",
    )
    @Sql(
        "/db/organization/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should return 404 when form is not found`(): Unit = runBlocking {
        val id = "a0654720-35dc-49d0-b508-1f7df5d915f2"
        val request = OrganizationStub.generateUpdateRequest()
        webTestClient.mutateWith(SecurityMockServerConfigurers.csrf()).put()
            .uri("$ENDPOINT/$id")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.type").isEqualTo("https://lyra.com/errors/entity-not-found")
            .jsonPath("$.title").isEqualTo("Entity not found")
            .jsonPath("$.status").isEqualTo(404)
            .jsonPath("$.detail").isEqualTo("Organization not found")
            .jsonPath("$.instance")
            .isEqualTo("$ENDPOINT/$id")
            .jsonPath("$.errorCategory").isEqualTo("NOT_FOUND")
            .jsonPath("$.timestamp").isNotEmpty
            .consumeWith { response ->
                println(response)
            }
    }
}
