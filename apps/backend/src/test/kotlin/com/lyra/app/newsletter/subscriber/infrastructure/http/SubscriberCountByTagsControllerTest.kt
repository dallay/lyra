package com.lyra.app.newsletter.subscriber.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.app.newsletter.subscriber.application.count.bytags.CountByTagsQuery
import com.lyra.app.newsletter.subscriber.application.count.bytags.SubscriberCountByTagsData
import com.lyra.app.newsletter.subscriber.application.count.bytags.SubscriberCountByTagsResponse
import io.mockk.coEvery
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

internal class SubscriberCountByTagsControllerTest : ControllerTest() {
    private val organizationId = "27172d5a-b88e-451c-9787-312706f4570d"
    private lateinit var controller: SubscriberCountByTagsController
    override lateinit var webTestClient: WebTestClient

    @BeforeEach
    override fun setUp() {
        super.setUp()
        coEvery { mediator.send(any(CountByTagsQuery::class)) } returns SubscriberCountByTagsResponse(
            listOf(
                SubscriberCountByTagsData("tag1", 10L),
                SubscriberCountByTagsData("tag2", 5L),
                SubscriberCountByTagsData("tag3", 98L),
            ),
        )
        controller = SubscriberCountByTagsController(mediator)
        webTestClient = buildWebTestClient(controller)
    }

    @Test
    fun `should count subscribers by tags`() {
        webTestClient.get()
            .uri("/api/organization/$organizationId/newsletter/subscriber/count-by-tags")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.data.length()").isEqualTo(3)
            .jsonPath("$.data[0].tag").isEqualTo("tag1")
            .jsonPath("$.data[0].count").isEqualTo(10)
            .jsonPath("$.data[1].tag").isEqualTo("tag2")
            .jsonPath("$.data[1].count").isEqualTo(5)
            .jsonPath("$.data[2].tag").isEqualTo("tag3")
            .jsonPath("$.data[2].count").isEqualTo(98)
    }
}
