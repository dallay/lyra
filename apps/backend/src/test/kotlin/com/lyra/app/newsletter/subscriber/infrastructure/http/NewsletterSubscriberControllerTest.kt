package com.lyra.app.newsletter.subscriber.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.UnitTest
import com.lyra.app.newsletter.subscriber.application.SubscribeNewsletterCommand
import com.lyra.app.newsletter.subscriber.infrastructure.http.request.SubscribeNewsletterRequest
import io.mockk.coEvery
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

@UnitTest
internal class NewsletterSubscriberControllerTest : ControllerTest() {
    private val id = UUID.randomUUID().toString()
    private val organizationId = UUID.randomUUID().toString()
    private val email = "john.doe@example.com"
    private val firstname = "John"
    private val lastname = "Doe"
    private val command =
        SubscribeNewsletterCommand(id, email, firstname, lastname, organizationId = organizationId)
    private val controller = NewsletterSubscriberController(mediator)
    override val webTestClient = WebTestClient.bindToController(controller).build()

    @BeforeEach
    override fun setUp() {
        coEvery { mediator.send(eq(command)) } returns Unit
    }

    @Test
    fun `should subscribe a new subscriber`() {
        val request = SubscribeNewsletterRequest(
            email = email,
            firstname = firstname,
            lastname = lastname,
        )

        webTestClient.put()
            .uri("/api/organization/$organizationId/newsletter/subscriber/$id")
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody().isEmpty

        coEvery { mediator.send(eq(command)) }
    }
}
