package com.lyra.app.newsletter.infrastructure.http

import com.lyra.app.ControllerIntegrationTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.context.jdbc.Sql

private const val ENDPOINT = "/api/newsletter/subscribers"

internal class GetAllSubscriberControllerIntegrationTest : ControllerIntegrationTest() {

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
                        .queryParam("page", 0)
                        .queryParam("perPage", 10)
                        .build()
                }
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isArray
                .jsonPath("$.total").isEqualTo(2)
                .jsonPath("$.perPage").isEqualTo(10)
                .jsonPath("$.page").isEqualTo(0)
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
            .jsonPath("$.total").isEqualTo(0)
            .jsonPath("$.perPage").isEqualTo(10)
            .jsonPath("$.page").isEqualTo(0)
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
                    .queryParam("email", listOf("eq:jana.doe@test.com"))
                    .queryParam("status", listOf("eq:ENABLED"))
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.total").isEqualTo(1)
            .jsonPath("$.perPage").isEqualTo(10)
            .jsonPath("$.page").isEqualTo(0)
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
            .jsonPath("$.total").isEqualTo(2)
            .jsonPath("$.perPage").isEqualTo(10)
            .jsonPath("$.page").isEqualTo(0)
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
}
