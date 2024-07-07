package com.lyra.app.team.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.app.organization.infrastructure.http.ENDPOINT_ORGANIZATION
import com.lyra.app.team.TeamStub
import com.lyra.app.team.application.TeamResponses
import com.lyra.app.team.application.find.all.AllTeamQuery
import com.lyra.app.team.domain.Team
import io.mockk.coEvery
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

internal class GetAllTeamControllerTest : ControllerTest() {
    private val controller: GetAllTeamController = GetAllTeamController(mediator)
    override val webTestClient: WebTestClient = buildWebTestClient(controller)
    private val organizationId = UUID.randomUUID().toString()
    private val teams: List<Team> =
        TeamStub.dummyRandomTeams(size = 6, organizationId = organizationId)

    @BeforeEach
    override fun setUp() {
        super.setUp()
        val query = AllTeamQuery(organizationId)
        coEvery { mediator.send(eq(query)) } returns TeamResponses.from(teams)
    }

    @Test
    fun `should get all teams`() {
        webTestClient
            .get()
            .uri("/api/$ENDPOINT_ORGANIZATION/$organizationId/$ENDPOINT_TEAM")
            .exchange()
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.data.length()").isEqualTo(teams.size)
    }
}
