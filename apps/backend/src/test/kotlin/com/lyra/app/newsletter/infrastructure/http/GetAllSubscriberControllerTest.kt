package com.lyra.app.newsletter.infrastructure.http

import com.lyra.app.newsletter.application.SubscriberResponse
import com.lyra.app.newsletter.application.SubscribersResponse
import com.lyra.app.newsletter.application.search.all.SearchAllSubscribersQuery
import com.lyra.common.domain.bus.Mediator
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import net.datafaker.Faker
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

private const val ENDPOINT = "/api/newsletter/subscribers"
class GetAllSubscriberControllerTest {
    private val mediator = mockk<Mediator>()
    private val faker = Faker()
    private val response = SubscribersResponse(
        dummySubscriberResponses(),
    )

    private val controller = GetAllSubscriberController(mediator)
    private val webTestClient = WebTestClient.bindToController(controller).build()
    @BeforeEach
    fun setUp() {
        coEvery { mediator.send(any(SearchAllSubscribersQuery::class)) } returns response
    }

    @Test
    fun `should get all subscribers`() {
        // Given
        // When
        webTestClient.get()
            .uri(ENDPOINT)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.subscribers").isArray
            .jsonPath("$.subscribers.length()").isEqualTo(response.subscribers.size)
            .jsonPath("$.subscribers[0].id").isEqualTo(response.subscribers[0].id)
            .jsonPath("$.subscribers[0].email").isEqualTo(response.subscribers[0].email)
            .jsonPath("$.subscribers[0].name").isEqualTo(response.subscribers[0].name)
            .jsonPath("$.subscribers[0].status").isEqualTo(response.subscribers[0].status)
        // Then
        coEvery { mediator.send(any(SearchAllSubscribersQuery::class)) }
    }

    private fun dummySubscriberResponses(size: Int = 10) = (0..size).map {
        SubscriberResponse(
            id = UUID.randomUUID().toString(),
            email = faker.internet().emailAddress(),
            name = faker.name().fullName(),
            status = faker.options().option("ENABLED", "DISABLED", "BLOCKLISTED"),
        )
    }
}
