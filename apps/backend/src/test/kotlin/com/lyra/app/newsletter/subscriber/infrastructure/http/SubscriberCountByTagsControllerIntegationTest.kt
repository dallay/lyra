package com.lyra.app.newsletter.subscriber.infrastructure.http

import com.lyra.ControllerIntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.test.context.jdbc.Sql

internal class SubscriberCountByTagsControllerIntegationTest : ControllerIntegrationTest() {
    private val organizationId = "1b423df9-d6fc-4fd9-904b-4bb40dc88aeb"

    @Test
    @Sql("/db/subscriber/subscriber-stats.sql")
    @Sql("/db/subscriber/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    fun `should count subscribers by tags`() {
        webTestClient.get()
            .uri("/api/organization/$organizationId/newsletter/subscriber/count-by-tags")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .consumeWith { response -> println(String(response.responseBody!!)) }
            .jsonPath("$.data").isArray
            .jsonPath("$.data.length()").isEqualTo(19)
            .apply {
                listOf(
                    "tag51" to 1, "tag89" to 1, "tag46" to 1, "tag14" to 1, "tag21" to 1,
                    "tag1" to 6, "tag41" to 1, "tag6" to 2, "tag2" to 2, "tag61" to 1,
                    "tag11" to 1, "tag5" to 9, "tag18" to 1, "tag3" to 12, "tag99" to 1,
                    "tag4" to 11, "tag7" to 1, "tag25" to 1, "tag63" to 1,
                ).forEachIndexed { index, (tag, count) ->
                    jsonPath("$.data[$index].tag").isEqualTo(tag)
                    jsonPath("$.data[$index].count").isEqualTo(count)
                }
            }
    }
}
