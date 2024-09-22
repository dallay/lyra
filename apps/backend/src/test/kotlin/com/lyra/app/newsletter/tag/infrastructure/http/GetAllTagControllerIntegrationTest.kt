package com.lyra.app.newsletter.tag.infrastructure.http

import com.lyra.ControllerIntegrationTest
import java.util.UUID
import org.junit.jupiter.api.Test
import org.springframework.test.context.jdbc.Sql

internal class GetAllTagControllerIntegrationTest : ControllerIntegrationTest() {
    private val organizationId = "a0654720-35dc-49d0-b508-1f7df5d915f1"
    private val uri = "/api/organization/$organizationId/tag"

    @Test
    @Sql("/db/subscriber/subscriber.sql", "/db/tag/tag.sql")
    @Sql("/db/subscriber/clean.sql", "/db/tag/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    fun `should get all tags for a specific organization`() {
        webTestClient.get()
            .uri(uri)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.data.length()").isEqualTo(3)
            .jsonPath("$.data[0].id").isEqualTo("16f86bda-45ac-4f9f-9658-4b359a1b98bf")
            .jsonPath("$.data[0].name").isEqualTo("Test: Free")
            .jsonPath("$.data[0].color").isEqualTo("purple")
            .jsonPath("$.data[0].organizationId").isEqualTo(organizationId)
            .jsonPath("$.data[0].subscribers").isArray
            .jsonPath("$.data[0].subscribers.length()").isEqualTo(2)
            .jsonPath("$.data[0].subscribers[0]").isEqualTo("john.doe@test.com")
            .jsonPath("$.data[0].subscribers[1]").isEqualTo("jana.doe@test.com")
            .jsonPath("$.data[0].createdAt").isEqualTo("2024-09-15T21:04:16.833")
            .jsonPath("$.data[0].updatedAt").isEqualTo("2024-09-15T21:04:16.861")
            .jsonPath("$.data[1].id").isEqualTo("331afd9a-b3b4-47b3-83cf-3fcb3ab9f926")
            .jsonPath("$.data[1].name").isEqualTo("Test: Premium")
            .jsonPath("$.data[1].color").isEqualTo("red")
            .jsonPath("$.data[1].organizationId").isEqualTo(organizationId)
            .jsonPath("$.data[1].subscribers").isArray
            .jsonPath("$.data[1].subscribers.length()").isEqualTo(1)
            .jsonPath("$.data[1].subscribers[0]").isEqualTo("john.doe@test.com")
            .jsonPath("$.data[1].createdAt").isEqualTo("2024-09-18T00:14:13.156")
            .jsonPath("$.data[1].updatedAt").isEqualTo("2024-09-18T00:14:13.176")
            .jsonPath("$.data[2].id").isEqualTo("d667bf8b-69d7-4e32-9488-8ad9865fc644")
            .jsonPath("$.data[2].name").isEqualTo("Test: Pay")
            .jsonPath("$.data[2].color").isEqualTo("blue")
            .jsonPath("$.data[2].organizationId").isEqualTo(organizationId)
            .jsonPath("$.data[2].subscribers").isArray
            .jsonPath("$.data[2].subscribers.length()").isEqualTo(1)
            .jsonPath("$.data[2].subscribers[0]").isEqualTo("john.doe@test.com")
            .jsonPath("$.data[2].createdAt").isEqualTo("2024-09-15T21:06:17.829")
            .jsonPath("$.data[2].updatedAt").isEqualTo("2024-09-15T21:06:17.850")
            .consumeWith(::println)
    }

    @Test
    @Sql("/db/subscriber/subscriber.sql", "/db/tag/tag.sql")
    @Sql("/db/subscriber/clean.sql", "/db/tag/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    fun `should return empty list when there are no tags for a specific organization`() {
        webTestClient.get()
            .uri("/api/organization/${UUID.randomUUID()}/tag")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.data.length()").isEqualTo(0)
            .consumeWith(::println)
    }
}
