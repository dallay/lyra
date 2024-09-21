package com.lyra.app.newsletter.subscriber.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.app.controllers.GlobalExceptionHandler
import com.lyra.app.newsletter.subscriber.SubscriberStub
import com.lyra.app.newsletter.subscriber.application.SubscriberResponse
import com.lyra.app.newsletter.subscriber.application.SubscribersResponse
import com.lyra.app.newsletter.subscriber.application.search.email.AllSubscribersByEmailQuery
import com.lyra.app.newsletter.subscriber.domain.Subscriber
import io.kotest.assertions.print.print
import io.mockk.coEvery
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

internal class GetAllSubscribersByEmailControllerTest : ControllerTest() {
    private lateinit var controller: GetAllSubscribersByEmailController
    override lateinit var webTestClient: WebTestClient
    private lateinit var subscribers: List<Subscriber>
    private val organizationId = "a0654720-35dc-49d0-b508-1f7df5d915f1"
    private val uri = "/api/organization/$organizationId/newsletter/subscriber/find-all-by-emails"

    @BeforeEach
    override fun setUp() {
        super.setUp()
        subscribers = SubscriberStub.dummyRandomSubscribersList(23)
        val response: SubscribersResponse =
            SubscribersResponse(subscribers.map { SubscriberResponse.from(it) })
        coEvery { mediator.send(any(AllSubscribersByEmailQuery::class)) } returns response
        controller = GetAllSubscribersByEmailController(mediator)
        webTestClient = WebTestClient.bindToController(controller)
            .controllerAdvice(GlobalExceptionHandler()) // Attach the global exception handler
            .build()
    }

    @Test
    fun `should get all subscribers by email`() {
        val emails = subscribers.map { it.email.value }
        val firstSubscriber = subscribers.first()
        webTestClient
            .get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(uri)
                    .queryParam("emails", emails)
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.subscribers").isArray
            .jsonPath("$.subscribers[0].id").isEqualTo(firstSubscriber.id.value.toString())
            .jsonPath("$.subscribers[0].email").isEqualTo(firstSubscriber.email.value)
            .jsonPath("$.subscribers[0].name").isEqualTo(firstSubscriber.name.toString())
            .jsonPath("$.subscribers[0].status").isEqualTo(firstSubscriber.status.toString())
    }

    @Test
    fun `should return bad request when emails list is empty`() {
        val emails = emptyList<String>()
        webTestClient
            .get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(uri)
                    .queryParam("emails", emails)
                    .build()
            }
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.title").isEqualTo("Bad Request")
            .jsonPath("$.status").isEqualTo(400)
            .jsonPath("$.detail").isEqualTo("Required query parameter 'emails' is not present.")
            .jsonPath("$.instance").isEqualTo(uri)
            .consumeWith {
                println(it.responseBody?.print())
            }
    }

    @Test
    fun `should return empty response when no subscribers are found`() {
        val emails = listOf("emailnofound@example.com")
        coEvery { mediator.send(any(AllSubscribersByEmailQuery::class)) } returns SubscribersResponse(emptyList())
        webTestClient
            .get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(uri)
                    .queryParam("emails", emails)
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.subscribers").isEmpty
    }
}
