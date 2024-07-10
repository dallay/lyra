package com.lyra.app.team.member.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.app.team.member.TeamMemberStub
import com.lyra.app.team.member.application.AddTeamMemberCommand
import com.lyra.app.team.member.infrastructure.http.request.AddTeamMemberRequest
import io.mockk.coEvery
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

internal class AddTeamMemberControllerTest : ControllerTest() {
    private var controller: AddTeamMemberController = AddTeamMemberController(mediator)
    override var webTestClient: WebTestClient = buildWebTestClient(controller)
    private val teamId = UUID.randomUUID().toString()
    private val command = AddTeamMemberCommand(
        teamId = teamId,
        userId = userId.toString(),
        role = "EDITOR",
    )

    @BeforeEach
    override fun setUp() {
        super.setUp()
        coEvery { mediator.send(command) } returns Unit
    }

    @Test
    fun `should add a new team member`() {
        val request: AddTeamMemberRequest =
            TeamMemberStub.generateRequest(
                teamId = teamId,
                userId = userId.toString(),
            )
        webTestClient.put()
            .uri("/api/$ENDPOINT_TEAM_MEMBER")
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody().isEmpty
        coEvery { mediator.send(command) }
    }
}
