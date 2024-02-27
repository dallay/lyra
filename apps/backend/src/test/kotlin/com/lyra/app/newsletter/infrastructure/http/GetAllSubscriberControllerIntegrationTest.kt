package com.lyra.app.newsletter.infrastructure.http

import com.lyra.app.ControllerIntegrationTest
import com.lyra.app.newsletter.SubscriberStub.subscriberResponsesByBatch
import com.lyra.app.newsletter.application.SubscriberResponse
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.common.domain.presentation.pagination.TimestampCursor
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.core.ParameterizedTypeReference
import org.springframework.test.context.jdbc.Sql

private const val ENDPOINT = "/api/newsletter/subscribers"

internal class GetAllSubscriberControllerIntegrationTest : ControllerIntegrationTest() {
    private val typeRef =
        object : ParameterizedTypeReference<CursorPageResponse<SubscriberResponse>>() {}

    @BeforeEach
    fun setUp() {
        // Set Test container here
    }

    @Test
    @Sql(
        "/db/subscriber.sql",
    )
    @Sql(
        "/db/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get all subscribers`() {
        webTestClient.run {
            get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path(ENDPOINT)
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
        "/db/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get empty list if no subscribers`() {

        webTestClient.get()
            .uri(ENDPOINT)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.nextPageCursor").doesNotExist()
            .consumeWith { println(it) }
    }

    @Test
    @Sql(
        "/db/subscriber.sql",
    )
    @Sql(
        "/db/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get all subscribers with filter`() {

        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(ENDPOINT)
                    .queryParam("filter[email]", listOf("eq:jana.doe@test.com"))
                    .queryParam("filter[status]", listOf("eq:ENABLED"))
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
        "/db/subscriber.sql",
    )
    @Sql(
        "/db/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get all subscribers by search`() {
        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(ENDPOINT)
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
        "/db/subscriber.sql",
    )
    @Sql(
        "/db/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should prioritize search over filters`() {
        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(ENDPOINT)
                    .queryParam("search", "jana")
                    .queryParam(
                        "filter[email]", listOf("eq:john.doe@test.com"),
                    )
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
        "/db/subscriber.sql",
    )
    @Sql(
        "/db/clean.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
    )
    fun `should get all subscribers with sort`() {
        val sort = listOf("asc:email", "desc:firstname", "asc:lastname", "desc:status")
        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(ENDPOINT)
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
        "/db/subscriber-pagination.sql",
    )
    @Sql(
        "/db/clean.sql",
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
        var nextPageCursor = TimestampCursor(LocalDateTime.parse("2023-02-28T15:44:03.616085"))
        assertEquals(nextPageCursor.serialize(), responseBody?.nextPageCursor)

        responseBody = makeRequestAndGetResponseBody(pageSize, responseBody?.nextPageCursor)

        data = responseBody?.data
        assertEquals(pageSize, data?.size)
        data?.let {
            compareSubscribers(
                it.toList(),
                subscriberResponsesByBatch(2),
            )
        }
        nextPageCursor = TimestampCursor(LocalDateTime.parse("2023-03-13T15:44:03.616085"))
        assertEquals(nextPageCursor.serialize(), responseBody?.nextPageCursor)

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
                    .path(ENDPOINT)
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
