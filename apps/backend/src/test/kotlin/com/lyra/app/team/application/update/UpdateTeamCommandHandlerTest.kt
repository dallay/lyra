package com.lyra.app.team.application.update

import com.lyra.UnitTest
import com.lyra.app.team.TeamStub
import com.lyra.app.team.domain.Team
import com.lyra.app.team.domain.TeamFinderRepository
import com.lyra.app.team.domain.TeamRepository
import com.lyra.app.team.domain.event.TeamUpdatedEvent
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class UpdateTeamCommandHandlerTest {
    private lateinit var eventPublisher: EventPublisher<TeamUpdatedEvent>
    private lateinit var teamRepository: TeamRepository
    private lateinit var teamFinderRepository: TeamFinderRepository
    private lateinit var teamUpdater: TeamUpdater
    private lateinit var updateTeamCommandHandler: UpdateTeamCommandHandler
    private lateinit var team: Team

    @BeforeEach
    fun setUp() {
        eventPublisher = mockk()
        teamRepository = mockk()
        teamFinderRepository = mockk()
        teamUpdater = TeamUpdater(teamRepository, teamFinderRepository, eventPublisher)
        updateTeamCommandHandler = UpdateTeamCommandHandler(teamUpdater)
        team = TeamStub.create()

        coEvery { teamRepository.update(any()) } returns Unit
        coEvery { teamFinderRepository.findById(any()) } returns team
        coEvery { eventPublisher.publish(any(TeamUpdatedEvent::class)) } returns Unit
    }

    @Test
    fun `should update a team`() = runBlocking {
        // Given
        val command = UpdateTeamCommand(
            teamId = team.id.value.toString(),
            name = team.name,
        )

        // When
        updateTeamCommandHandler.handle(command)

        // Then
        coVerify(exactly = 1) {
            teamRepository.update(
                withArg {
                    assertEquals(team.id, it.id)
                    assertEquals(team.name, it.name)
                    assertEquals(team.organizationId, it.organizationId)
                    assertEquals(team.members, it.members)
                },
            )
            eventPublisher.publish(ofType(TeamUpdatedEvent::class))
        }
    }
}
