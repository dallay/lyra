package com.lyra.app.newsletter.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.app.newsletter.application.count.bystatus.CountByStatusQuery
import com.lyra.app.newsletter.application.count.bystatus.SubscriberCountByStatusData
import com.lyra.app.newsletter.application.count.bystatus.SubscriberCountByStatusResponse
import io.mockk.coEvery
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

internal class SubscriberCountByStatusControllerTest : ControllerTest() {
    private val organizationId = "27172d5a-b88e-451c-9787-312706f4570d"
    private lateinit var controller: SubscriberCountByStatusController
    override lateinit var webTestClient: WebTestClient

    @BeforeEach
    override fun setUp() {
        super.setUp()
        coEvery { mediator.send(any(CountByStatusQuery::class)) } returns SubscriberCountByStatusResponse(
            listOf(
                SubscriberCountByStatusData("ENABLED", 10L),
                SubscriberCountByStatusData("DISABLED", 5L),
                SubscriberCountByStatusData("BLOCKLISTED", 98L),
            ),
        )

        controller = SubscriberCountByStatusController(mediator)
        webTestClient = buildWebTestClient(controller)
    }

    @Test
    fun `should count subscribers by status`() {
        webTestClient.get()
            .uri("/api/organization/$organizationId/newsletter/subscriber/count-by-status")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.data.length()").isEqualTo(3)
            .jsonPath("$.data[0].status").isEqualTo("ENABLED")
            .jsonPath("$.data[0].count").isEqualTo(10)
            .jsonPath("$.data[1].status").isEqualTo("DISABLED")
            .jsonPath("$.data[1].count").isEqualTo(5)
            .jsonPath("$.data[2].status").isEqualTo("BLOCKLISTED")
            .jsonPath("$.data[2].count").isEqualTo(98)
    }
}
