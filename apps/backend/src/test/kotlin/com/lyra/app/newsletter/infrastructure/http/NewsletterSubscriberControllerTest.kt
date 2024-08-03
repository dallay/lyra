package com.lyra.app.newsletter.infrastructure.http

import com.lyra.UnitTest
import com.lyra.app.newsletter.application.SubscribeNewsletterCommand
import com.lyra.app.newsletter.infrastructure.http.request.SubscribeNewsletterRequest
import com.lyra.common.domain.bus.Mediator
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

private const val ENDPOINT = "/api/newsletter/subscribers"

@UnitTest
internal class NewsletterSubscriberControllerTest {
    private val mediator = mockk<Mediator>()
    private val id = UUID.randomUUID().toString()
    private val organizationId = UUID.randomUUID().toString()
    private val email = "john.doe@example.com"
    private val firstname = "John"
    private val lastname = "Doe"
    private val command = SubscribeNewsletterCommand(id, email, firstname, lastname, organizationId)
    private val controller = NewsletterSubscriberController(mediator)
    private val webTestClient = WebTestClient.bindToController(controller).build()

    @BeforeEach
    fun setUp() {
        coEvery { mediator.send(eq(command)) } returns Unit
    }

    @Test
    fun `should subscribe a new subscriber`() {
        val request = SubscribeNewsletterRequest(
            email = email,
            firstname = firstname,
            lastname = lastname,
            organizationId = organizationId,
        )

        webTestClient.put()
            .uri("$ENDPOINT/$id")
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody().isEmpty

        coEvery { mediator.send(eq(command)) }
    }
}
