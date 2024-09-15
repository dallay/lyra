package com.lyra.app.newsletter.infrastructure.http

import com.lyra.ControllerIntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.test.context.jdbc.Sql

internal class SubscriberCountByStatusControllerIntegrationTest : ControllerIntegrationTest() {
    private val organizationId = "1b423df9-d6fc-4fd9-904b-4bb40dc88aeb"

    @Test
    @Sql(
        "/db/subscriber/subscriber-stats.sql",
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
            .consumeWith { response -> println(String(response.responseBody!!)) }
            .jsonPath("$.data").isArray
            .jsonPath("$.data.length()").isEqualTo(3)
            .jsonPath("$.data[0].status").isEqualTo("ENABLED")
            .jsonPath("$.data[0].count").isEqualTo(12)
            .jsonPath("$.data[1].status").isEqualTo("DISABLED")
            .jsonPath("$.data[1].count").isEqualTo(10)
            .jsonPath("$.data[2].status").isEqualTo("BLOCKLISTED")
            .jsonPath("$.data[2].count").isEqualTo(5)
    }
}
