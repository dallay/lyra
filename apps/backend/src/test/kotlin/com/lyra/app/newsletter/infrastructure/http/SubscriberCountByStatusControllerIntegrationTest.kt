package com.lyra.app.newsletter.infrastructure.http

import com.lyra.ControllerIntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.test.context.jdbc.Sql

internal class SubscriberCountByStatusControllerIntegrationTest : ControllerIntegrationTest() {
    private val organizationId = "27172d5a-b88e-451c-9787-312706f4570d"

    @Test
    @Sql(
        "/db/subscriber/subscriber-count-by-status.sql",
    )
    @Sql(
        "/db/subscriber/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should count subscribers by status`() {
        webTestClient.get()
            .uri("/api/organization/$organizationId/newsletter/subscriber/count-by-status")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.data.length()").isEqualTo(3)
            .jsonPath("$.data[0].status").isEqualTo("DISABLED")
            .jsonPath("$.data[0].count").isEqualTo(10)
            .jsonPath("$.data[1].status").isEqualTo("ENABLED")
            .jsonPath("$.data[1].count").isEqualTo(12)
            .jsonPath("$.data[2].status").isEqualTo("BLOCKLISTED")
            .jsonPath("$.data[2].count").isEqualTo(5)
    }
}
