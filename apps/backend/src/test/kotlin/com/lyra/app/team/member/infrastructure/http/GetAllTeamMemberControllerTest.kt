package com.lyra.app.team.member.infrastructure.http

import com.lyra.ControllerTest
import com.lyra.app.team.member.application.OrganizationTeamMemberResponse
import com.lyra.app.team.member.application.OrganizationTeamMemberResponses
import com.lyra.app.team.member.application.all.GetAllTeamMember
import io.mockk.coEvery
import java.util.*
import net.datafaker.Faker
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

internal class GetAllTeamMemberControllerTest : ControllerTest() {
    private var controller: GetAllTeamMemberController = GetAllTeamMemberController(mediator)
    override var webTestClient: WebTestClient = buildWebTestClient(controller)
    private val query = GetAllTeamMember(userId.toString())
    private val organizationId = UUID.randomUUID()
    private val organizationId1 = UUID.randomUUID()
    private val teamId = UUID.randomUUID()
    private val teamId1 = UUID.randomUUID()
    private val organizationOwnerId = UUID.randomUUID()
    private val faker = Faker()
    private val organizationTeamMember = OrganizationTeamMemberResponse(
        teamName = faker.team().name(),
        organizationName = faker.company().name(),
        role = "OWNER",
        organizationId = organizationId.toString(),
        organizationOwnerId = userId.toString(),
        teamId = teamId.toString(),
        userId = userId.toString(),
    )
    private val organizationTeamMember1 = OrganizationTeamMemberResponse(
        teamName = faker.team().name(),
        organizationName = faker.company().name(),
        role = "EDITOR",
        organizationId = organizationId1.toString(),
        organizationOwnerId = organizationOwnerId.toString(),
        teamId = teamId1.toString(),
        userId = userId.toString(),
    )

    @BeforeEach
    override fun setUp() {
        super.setUp()

        coEvery { mediator.send(query) } returns OrganizationTeamMemberResponses(
            listOf(
                organizationTeamMember,
                organizationTeamMember1,
            ),
        )
    }

    @Test
    fun `should get all team members`() {
        webTestClient.get()
            .uri("/api/$ENDPOINT_TEAM_MEMBER")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data[0].teamName").isEqualTo(organizationTeamMember.teamName)
            .jsonPath("$.data[0].organizationName")
            .isEqualTo(organizationTeamMember.organizationName)
            .jsonPath("$.data[0].role").isEqualTo(organizationTeamMember.role)
            .jsonPath("$.data[0].organizationId").isEqualTo(organizationTeamMember.organizationId)
            .jsonPath("$.data[0].organizationOwnerId")
            .isEqualTo(organizationTeamMember.organizationOwnerId)
            .jsonPath("$.data[0].teamId").isEqualTo(organizationTeamMember.teamId)
            .jsonPath("$.data[0].userId").isEqualTo(organizationTeamMember.userId)
            .jsonPath("$.data[1].teamName").isEqualTo(organizationTeamMember1.teamName)
            .jsonPath("$.data[1].organizationName")
            .isEqualTo(organizationTeamMember1.organizationName)
            .jsonPath("$.data[1].role").isEqualTo(organizationTeamMember1.role)
            .jsonPath("$.data[1].organizationId").isEqualTo(organizationTeamMember1.organizationId)
            .jsonPath("$.data[1].organizationOwnerId")
            .isEqualTo(organizationTeamMember1.organizationOwnerId)
            .jsonPath("$.data[1].teamId").isEqualTo(organizationTeamMember1.teamId)
            .jsonPath("$.data[1].userId").isEqualTo(organizationTeamMember1.userId)
    }
}
