package com.lyra.app.organization.infrastructure.http

import com.lyra.ControllerIntegrationTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.test.context.jdbc.Sql

internal class FindOrganizationControllerIntegrationTest : ControllerIntegrationTest() {

    @Test
    @Sql(
        "/db/organization/organization.sql",
    )
    @Sql(
        "/db/organization/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should return organization when organization is found`(): Unit = runBlocking {
        val id = "a0654720-35dc-49d0-b508-1f7df5d915f1"
        webTestClient.get()
            .uri("/api/organization/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id)
            .jsonPath("$.name").isEqualTo("Test: My First Workspace")
            .jsonPath("$.userId").isEqualTo("efc4b2b8-08be-4020-93d5-f795762bf5c9")
            .jsonPath("$.createdAt").isNotEmpty
            .jsonPath("$.updatedAt").isNotEmpty
            .consumeWith { response ->
                println(response)
            }
    }

    @Test
    fun `should return 404 when organization is not found`(): Unit = runBlocking {
        val id = "94be1a32-cf2e-4dfc-892d-bdd8ac7ad354"
        webTestClient.get()
            .uri("/api/organization/$id")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.type").isEqualTo("https://lyra.com/errors/entity-not-found")
            .jsonPath("$.title").isEqualTo("Entity not found")
            .jsonPath("$.status").isEqualTo(404)
            .jsonPath("$.detail").isEqualTo("Workspace not found")
            .jsonPath("$.instance")
            .isEqualTo("/api/$ENDPOINT_ORGANIZATION/$id")
            .jsonPath("$.errorCategory").isEqualTo("NOT_FOUND")
            .jsonPath("$.timestamp").isNotEmpty
            .consumeWith { response ->
                println(response)
            }
    }
}
