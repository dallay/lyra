package com.lyra.app.team.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.app.team.application.delete.DeleteTeamCommand
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.slot
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

internal class DeleteTeamControllerTest : ControllerTest() {
    private lateinit var controller: DeleteTeamController
    override lateinit var webTestClient: WebTestClient
    private val id = UUID.randomUUID().toString()
    private val command = DeleteTeamCommand(id)

    @BeforeEach
    override fun setUp() {
        super.setUp()
        controller = DeleteTeamController(mediator)
        webTestClient = WebTestClient.bindToController(controller).build()
        coEvery { mediator.send(command) } returns Unit
    }

    @Test
    fun `should delete a team`() {
        webTestClient.delete()
            .uri("/api/team/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty()

        val commandSlot = slot<DeleteTeamCommand>()
        coVerify(exactly = 1) { mediator.send(capture(commandSlot)) }
        assertEquals(command, commandSlot.captured)
    }
}
