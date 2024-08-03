package com.lyra.app.organization.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.UnitTest
import com.lyra.app.organization.application.delete.DeleteOrganizationCommand
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.slot
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

@UnitTest
internal class DeleteOrganizationControllerTest : ControllerTest() {
    private lateinit var controller: DeleteOrganizationController
    override lateinit var webTestClient: WebTestClient
    private val id = UUID.randomUUID().toString()
    private val command = DeleteOrganizationCommand(id)

    @BeforeEach
    override fun setUp() {
        super.setUp()
        controller = DeleteOrganizationController(mediator)
        webTestClient = WebTestClient.bindToController(controller).build()
        coEvery { mediator.send(command) } returns Unit
    }

    @Test
    fun `should delete organization`() {
        webTestClient.delete()
            .uri("/api/organization/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty()

        val commandSlot = slot<DeleteOrganizationCommand>()
        coVerify(exactly = 1) { mediator.send(capture(commandSlot)) }
        assertEquals(command, commandSlot.captured)
    }
}
