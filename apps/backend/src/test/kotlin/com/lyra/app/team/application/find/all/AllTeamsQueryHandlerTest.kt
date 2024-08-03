package com.lyra.app.team.application.find.all

import com.lyra.UnitTest
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.app.team.TeamStub
import com.lyra.app.team.domain.Team
import com.lyra.app.team.domain.TeamFinderRepository
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class AllTeamsQueryHandlerTest {
    private lateinit var repository: TeamFinderRepository
    private lateinit var finder: AllTeamFinder
    private lateinit var handler: AllTeamsQueryHandler
    private lateinit var teams: List<Team>
    private lateinit var organizationId: OrganizationId

    @BeforeEach
    fun setUp() {
        repository = mockk()
        finder = AllTeamFinder(repository)
        handler = AllTeamsQueryHandler(finder)
        teams = TeamStub.dummyRandomTeams(6)
        organizationId = OrganizationId(UUID.randomUUID().toString())

        coEvery { repository.findAllTeamsThatBelongToOrganization(organizationId) } returns teams
    }

    @Test
    fun `should find all teams`() = runBlocking {
        // Given
        val query = AllTeamQuery(organizationId.value.toString())

        // When
        val response = handler.handle(query)

        // Then
        assertEquals(teams.size, response.data.size)
    }
}
