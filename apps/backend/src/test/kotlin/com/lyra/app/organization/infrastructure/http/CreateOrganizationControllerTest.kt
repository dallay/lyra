package com.lyra.app.organization.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.UnitTest
import com.lyra.app.organization.OrganizationStub
import com.lyra.app.organization.application.CreateOrganizationCommand
import com.lyra.app.organization.infrastructure.http.request.CreateOrganizationRequest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.slot
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class CreateOrganizationControllerTest : ControllerTest() {
    private val organization = OrganizationStub.create()
    private val id = UUID.randomUUID().toString()
    private val command = CreateOrganizationCommand(
        id = id,
        name = organization.name,
        userId = organization.userId.value.toString(),
    )
    private val controller = CreateOrganizationController(mediator)
    override val webTestClient = buildWebTestClient(controller)

    @BeforeEach
    override fun setUp() {
        super.setUp()
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

        val commandSlot = slot<CreateOrganizationCommand>()
        coVerify(exactly = 1) { mediator.send(capture(commandSlot)) }
        assertEquals(command, commandSlot.captured)
    }
}
