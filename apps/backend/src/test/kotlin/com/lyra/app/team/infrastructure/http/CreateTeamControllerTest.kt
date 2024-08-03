package com.lyra.app.team.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.app.team.TeamStub
import com.lyra.app.team.application.CreateTeamCommand
import com.lyra.app.team.infrastructure.http.request.CreateTeamRequest
import io.mockk.coEvery
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CreateTeamControllerTest : ControllerTest() {
    private val team = TeamStub.create()
    private val id = UUID.randomUUID().toString()
    private val command = CreateTeamCommand(
        teamId = id,
        name = team.name,
        organizationId = team.organizationId.value.toString(),
    )
    private val controller = CreateTeamController(mediator)
    override val webTestClient = buildWebTestClient(controller)

    @BeforeEach
    override fun setUp() {
        super.setUp()
        coEvery { mediator.send(eq(command)) } returns Unit
    }

    @Test
    fun `should create a new team`() {
        val request = CreateTeamRequest(
            name = team.name,
            organizationId = team.organizationId.value.toString(),
        )
        webTestClient.put()
            .uri("/api/team/$id")
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody().isEmpty
        coEvery { mediator.send(eq(command)) }
    }
}
