package com.lyra.app.team.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.UnitTest
import com.lyra.app.team.TeamStub
import com.lyra.app.team.application.update.UpdateTeamCommand
import com.lyra.app.team.infrastructure.http.request.UpdateTeamRequest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.slot
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

@UnitTest
internal class UpdateTeamControllerTest : ControllerTest() {
    private val team = TeamStub.create()
    private val id = UUID.randomUUID().toString()
    private val command = UpdateTeamCommand(teamId = id, name = team.name)
    private val controller = UpdateTeamController(mediator)
    override val webTestClient: WebTestClient = buildWebTestClient(controller)

    @BeforeEach
    override fun setUp() {
        super.setUp()
        coEvery { mediator.send(eq(command)) } returns Unit
    }

    @Test
    fun `should return 200 when a team is updated successfully`() {
        val request = UpdateTeamRequest(name = team.name)
        webTestClient.put()
            .uri("/api/team/update/$id")
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .isEqualTo("Team updated successfully")

        val commandSlot = slot<UpdateTeamCommand>()
        coVerify(exactly = 1) { mediator.send(capture(commandSlot)) }
        assertEquals(command, commandSlot.captured)
    }
}
