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

        webTestClient.get()
            .uri(ENDPOINT)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.subscribers").isArray
            .jsonPath("$.subscribers.length()").isEqualTo(2)
            .jsonPath("$.subscribers[0].id").isEqualTo("d73e2961-ec29-4f19-b5c4-b9c2dc7f1dee")
            .jsonPath("$.subscribers[0].email").isEqualTo("john.doe@test.com")
            .jsonPath("$.subscribers[0].name").isEqualTo("John Doe")
            .jsonPath("$.subscribers[0].status").isEqualTo("ENABLED")
            .jsonPath("$.subscribers[1].id").isEqualTo("d73e2961-ec29-4f19-b5c4-b9c2dc7f1def")
            .jsonPath("$.subscribers[1].email").isEqualTo("jana.doe@test.com")
            .jsonPath("$.subscribers[1].name").isEqualTo("Jana Doe")
            .jsonPath("$.subscribers[1].status").isEqualTo("ENABLED")
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
            .jsonPath("$.subscribers").isArray
            .jsonPath("$.subscribers.length()").isEqualTo(0)
    }
}
