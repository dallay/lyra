package com.lyra.app.team.member.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.app.team.member.application.remove.RemoveTeamMemberCommand
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.slot
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

internal class RemoveTeamMemberControllerTest : ControllerTest() {
    private lateinit var controller: RemoveTeamMemberController
    override lateinit var webTestClient: WebTestClient
    private val teamId = UUID.randomUUID().toString()

    private val command: RemoveTeamMemberCommand = RemoveTeamMemberCommand(
        teamId = teamId,
        userId = userId.toString(),
    )
    @BeforeEach
    override fun setUp() {
        super.setUp()
        controller = RemoveTeamMemberController(mediator)
        webTestClient = buildWebTestClient(controller)

        coEvery { mediator.send(command) } returns Unit
    }

    @Test
    fun `should remove a member from a team`() {
        webTestClient.delete()
            .uri("/api//team/$teamId/member/$userId")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty()

        val commandSlot = slot<RemoveTeamMemberCommand>()
        coVerify(exactly = 1) { mediator.send(capture(commandSlot)) }
        assertEquals(command, commandSlot.captured)
    }
}
