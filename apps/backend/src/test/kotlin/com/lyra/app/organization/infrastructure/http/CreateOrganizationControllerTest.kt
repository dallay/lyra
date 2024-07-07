package com.lyra.app.organization.infrastructure.http

import com.lyra.UnitTest
import com.lyra.app.organization.OrganizationStub
import com.lyra.app.organization.application.OrganizationCommand
import com.lyra.app.organization.infrastructure.http.request.CreateOrganizationRequest
import com.lyra.common.domain.bus.Mediator
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

@UnitTest
internal class CreateOrganizationControllerTest {
    private val organization = OrganizationStub.create()
    private val mediator = mockk<Mediator>()
    private val id = UUID.randomUUID().toString()
    private val command = OrganizationCommand(
        id = id,
        name = organization.name,
        userId = organization.userId.value.toString(),
    )
    private val controller = CreateOrganizationController(mediator)
    private val webTestClient = WebTestClient.bindToController(controller).build()

    @BeforeEach
    fun setUp() {
        coEvery { mediator.send(eq(command)) } returns Unit
    }

    @Test
    fun `should create a new organization`() {
        val request = CreateOrganizationRequest(
            name = organization.name,
            userId = organization.userId.value.toString(),
        )
        webTestClient.put()
            .uri("/api/organization/$id")
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody().isEmpty
        coEvery { mediator.send(eq(command)) }
    }
}
