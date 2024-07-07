package com.lyra.app.team.application.find

import com.lyra.UnitTest
import com.lyra.app.team.TeamStub
import com.lyra.app.team.application.TeamResponse
import com.lyra.app.team.domain.TeamId
import com.lyra.app.team.domain.exception.TeamNotFoundException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class FindTeamQueryHandlerTest {
    private lateinit var teamFinder: TeamFinder
    private lateinit var findTeamQueryHandler: FindTeamQueryHandler

    @BeforeEach
    fun setUp() {
        teamFinder = mockk()
        findTeamQueryHandler = FindTeamQueryHandler(teamFinder)
    }

    @Test
    fun `should return team response when team is found`() = runBlocking {
        // Given
        val id = UUID.randomUUID().toString()
        val teamId = TeamId(id)
        val team = TeamStub.create()
        val teamResponse = TeamResponse.from(team)
        coEvery { teamFinder.find(teamId) } returns team
        // When
        val result = findTeamQueryHandler.handle(FindTeamQuery(id))
        // Then
        assertEquals(teamResponse, result)
        coVerify(exactly = 1) { teamFinder.find(teamId) }
    }

    @Test
    fun `should throw exception when team is not found`() {
        // Given
        val id = UUID.randomUUID().toString()
        val teamId = TeamId(id)
        coEvery { teamFinder.find(teamId) } returns null
        // Then
        assertThrows(TeamNotFoundException::class.java) {
            // When
            runBlocking {
                findTeamQueryHandler.handle(FindTeamQuery(id))
            }
        }
        coVerify(exactly = 1) { teamFinder.find(teamId) }
    }
}
