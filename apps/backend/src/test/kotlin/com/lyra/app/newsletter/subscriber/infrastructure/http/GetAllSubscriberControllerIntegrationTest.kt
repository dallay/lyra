package com.lyra.app.newsletter.subscriber.infrastructure.http

import com.lyra.ControllerIntegrationTest
import com.lyra.app.newsletter.subscriber.SubscriberStub.subscriberResponsesByBatch
import com.lyra.app.newsletter.subscriber.application.SubscriberResponse
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.common.domain.presentation.pagination.FilterCondition
import com.lyra.common.domain.presentation.pagination.LogicalOperator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.core.ParameterizedTypeReference
import org.springframework.test.context.jdbc.Sql

internal class GetAllSubscriberControllerIntegrationTest : ControllerIntegrationTest() {
    private val typeRef =
        object : ParameterizedTypeReference<CursorPageResponse<SubscriberResponse>>() {}
    private val organizationId = "a0654720-35dc-49d0-b508-1f7df5d915f1"
    private val organizationIdBatch = "1b423df9-d6fc-4fd9-904b-4bb40dc88aeb"

    @Test
    @Sql(
        "/db/subscriber/subscriber.sql",
    )
    @Sql(
        "/db/subscriber/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get all subscribers`() {
        webTestClient.run {
            get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path("/api/organization/$organizationId/newsletter/subscriber")
                        .queryParam("size", 10)
                        .build()
                }
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isArray
                .jsonPath("$.nextPageCursor").doesNotExist()
                .jsonPath("$.data[0].id").isEqualTo("d73e2961-ec29-4f19-b5c4-b9c2dc7f1dee")
                .jsonPath("$.data[0].email").isEqualTo("john.doe@test.com")
                .jsonPath("$.data[0].name").isEqualTo("John Doe")
                .jsonPath("$.data[0].status").isEqualTo("ENABLED")
                .jsonPath("$.data[1].id").isEqualTo("d73e2961-ec29-4f19-b5c4-b9c2dc7f1def")
                .jsonPath("$.data[1].email").isEqualTo("jana.doe@test.com")
                .jsonPath("$.data[1].name").isEqualTo("Jana Doe")
                .jsonPath("$.data[1].status").isEqualTo("ENABLED")
                .consumeWith { println(it) }
        }
    }

    @Test
    @Sql(
        "/db/subscriber/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get empty list if no subscribers`() {

        webTestClient.get()
            .uri("/api/organization/$organizationId/newsletter/subscriber")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.nextPageCursor").doesNotExist()
            .consumeWith { println(it) }
    }

    @Test
    @Sql(
        "/db/subscriber/subscriber.sql",
    )
    @Sql(
        "/db/subscriber/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get all subscribers with filter`() {

        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/api/organization/$organizationId/newsletter/subscriber")
                    .queryParam("filter[email]", listOf("eq:jana.doe@test.com"))
                    .queryParam("filter[status]", listOf("OR:eq:ENABLED,eq:DISABLED"))
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.nextPageCursor").doesNotExist()
            .jsonPath("$.data[0].id").isEqualTo("d73e2961-ec29-4f19-b5c4-b9c2dc7f1def")
            .jsonPath("$.data[0].email").isEqualTo("jana.doe@test.com")
            .jsonPath("$.data[0].name").isEqualTo("Jana Doe")
            .jsonPath("$.data[0].status").isEqualTo("ENABLED")
            .consumeWith { println(it) }
    }

    @Test
    @Sql(
        "/db/subscriber/subscriber.sql",
    )
    @Sql(
        "/db/subscriber/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get all subscribers by search`() {
        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/api/organization/$organizationId/newsletter/subscriber")
                    .queryParam("search", "jana")
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.nextPageCursor").doesNotExist()
            .jsonPath("$.data[0].id").isEqualTo("d73e2961-ec29-4f19-b5c4-b9c2dc7f1def")
            .jsonPath("$.data[0].email").isEqualTo("jana.doe@test.com")
            .jsonPath("$.data[0].name").isEqualTo("Jana Doe")
            .jsonPath("$.data[0].status").isEqualTo("ENABLED")
            .consumeWith { println(it) }
    }

    @Test
    @Sql(
        "/db/subscriber/subscriber-pagination.sql",
    )
    @Sql(
        "/db/subscriber/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should combine search and filters`() {
        val filterCondition = FilterCondition(
            operator = LogicalOperator.AND,
            values = listOf(
                "gte:2023-03-12T23:00:00.000Z",
                "lte:2023-03-17T23:00:00.000Z",
            ),
        ).toString()

        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/api/organization/$organizationIdBatch/newsletter/subscriber")
                    .queryParam("search", "Henry")
                    .queryParam("filter[createdAt]", filterCondition)
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.nextPageCursor").doesNotExist()
            .jsonPath("$.data[0].id").isEqualTo("a72d3adb-7f07-4837-b592-0be854d20a67")
            .jsonPath("$.data[0].email").isEqualTo("maurice.henry@test.com")
            .jsonPath("$.data[0].name").isEqualTo("Maurice Henry")
            .jsonPath("$.data[0].status").isEqualTo("ENABLED")
            .jsonPath("$.data[1].id").isEqualTo("a4053f51-ddee-4abc-bf5d-767d7588b711")
            .jsonPath("$.data[1].email").isEqualTo("michel.henry@test.com")
            .jsonPath("$.data[1].name").isEqualTo("Michel Henry")
            .jsonPath("$.data[1].status").isEqualTo("ENABLED")
            .consumeWith { println(it) }
    }

    @Test
    @Sql(
        "/db/subscriber/subscriber.sql",
    )
    @Sql(
        "/db/subscriber/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get all subscribers with sort`() {
        val sort = listOf("asc:email", "desc:firstname", "asc:lastname", "desc:status")
        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/api/organization/$organizationId/newsletter/subscriber")
                    .queryParam("sort", sort)
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.nextPageCursor").doesNotExist()
            .jsonPath("$.data[0].id").isEqualTo("d73e2961-ec29-4f19-b5c4-b9c2dc7f1def")
            .jsonPath("$.data[0].email").isEqualTo("jana.doe@test.com")
            .jsonPath("$.data[0].name").isEqualTo("Jana Doe")
            .jsonPath("$.data[0].status").isEqualTo("ENABLED")
            .jsonPath("$.data[1].id").isEqualTo("d73e2961-ec29-4f19-b5c4-b9c2dc7f1dee")
            .jsonPath("$.data[1].email").isEqualTo("john.doe@test.com")
            .jsonPath("$.data[1].name").isEqualTo("John Doe")
            .jsonPath("$.data[1].status").isEqualTo("ENABLED")
            .consumeWith { println(it) }
    }

    @Test
    @Sql(
        "/db/subscriber/subscriber-pagination.sql",
    )
    @Sql(
        "/db/subscriber/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should forward pagination by cursor`() {
        val pageSize = 5
        var responseBody = makeRequestAndGetResponseBody(pageSize, null)

        var data = responseBody?.data
        assertEquals(pageSize, data?.size)
        data?.let {
            compareSubscribers(
                it.toList(),
                subscriberResponsesByBatch(),
            )
        }
        var cursor = responseBody?.nextPageCursor
        assertNotNull(cursor)

        responseBody = makeRequestAndGetResponseBody(pageSize, cursor)

        data = responseBody?.data
        assertEquals(pageSize, data?.size)
        data?.let {
            compareSubscribers(
                it.toList(),
                subscriberResponsesByBatch(2),
            )
        }

        cursor = responseBody?.nextPageCursor
        assertNotNull(cursor)

        responseBody = makeRequestAndGetResponseBody(pageSize, responseBody?.nextPageCursor)

        data = responseBody?.data
        assertEquals(2, data?.size)
        data?.let {
            compareSubscribers(
                it.toList(),
                subscriberResponsesByBatch(3),
            )
        }
        assertNull(responseBody?.nextPageCursor)
    }

    private fun compareSubscribers(
        subscribers: List<SubscriberResponse>,
        dbSubscribers: List<SubscriberResponse>
    ) {
        assertEquals(subscribers.size, dbSubscribers.size)

        // Sort both lists by id before comparing
        val sortedSubscribers = subscribers.sortedBy { it.id }
        val sortedDbSubscribers = dbSubscribers.sortedBy { it.id }

        // Compare each SubscriberResponse in the list to the data in the database
        for (i in sortedSubscribers.indices) {
            val subscriber = sortedSubscribers[i]
            val dbSubscriber = sortedDbSubscribers[i]

            assertEquals(subscriber.id, dbSubscriber.id)
            assertEquals(subscriber.email, dbSubscriber.email)
            assertEquals(subscriber.name, dbSubscriber.name)
            assertEquals(subscriber.status, dbSubscriber.status)
        }
    }

    private fun makeRequestAndGetResponseBody(
        size: Int = 10,
        cursor: String? = null
    ): CursorPageResponse<SubscriberResponse>? {
        return webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/api/organization/$organizationIdBatch/newsletter/subscriber")
                    .queryParam("size", size)
                    .apply { if (cursor != null) queryParam("cursor", cursor) }
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody(typeRef)
            .returnResult()
            .responseBody
    }
}
