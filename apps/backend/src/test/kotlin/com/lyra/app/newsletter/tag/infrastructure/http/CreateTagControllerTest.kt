package com.lyra.app.newsletter.tag.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.app.newsletter.tag.TagStub
import com.lyra.app.newsletter.tag.application.CreateTagCommand
import com.lyra.app.newsletter.tag.domain.Tag
import com.lyra.app.newsletter.tag.infrastructure.http.request.CreateTagRequest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

internal class CreateTagControllerTest : ControllerTest() {
    private val controller = CreateTagController(mediator)
    override val webTestClient: WebTestClient = buildWebTestClient(controller)
    private val subscriberEmails = listOf("newSubscriber@example.com")

    private val tag: Tag = TagStub.create()
    private val uri = "/api/organization/${tag.organizationId.value}/tag/${tag.id.value}"
    private val command = CreateTagCommand(
        id = tag.id.value.toString(),
        name = tag.name,
        color = tag.color.value,
        organizationId = tag.organizationId.value.toString(),
        subscribers = subscriberEmails,
    )

    @BeforeEach
    override fun setUp() {
        super.setUp()
        coEvery { mediator.send(any<CreateTagCommand>()) } returns Unit
    }

    @Test
    fun `should create a new tag`() {
        val request = CreateTagRequest(
            name = tag.name,
            color = tag.color.value,
            subscribers = subscriberEmails,
        )

        webTestClient.put()
            .uri(uri)
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody().isEmpty
        val commandSlot = slot<CreateTagCommand>()
        coVerify(exactly = 1) { mediator.send(capture(commandSlot)) }
        assertEquals(command, commandSlot.captured)
    }

    @Test
    fun `should not create a new tag if name is empty`() {
        val request = CreateTagRequest(
            name = "",
            color = tag.color.value,
            subscribers = subscriberEmails,
        )

        webTestClient.put()
            .uri(uri)
            .bodyValue(request)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.title").isEqualTo("Bad Request")
            .jsonPath("$.status").isEqualTo(400)
            .jsonPath("$.instance").isEqualTo(uri)
            .consumeWith(::println)
    }
}
