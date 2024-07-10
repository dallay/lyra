package com.lyra.app.team.application

import com.lyra.UnitTest
import com.lyra.app.team.domain.TeamRepository
import com.lyra.app.team.domain.event.TeamCreatedEvent
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class CreateTeamCommandHandlerTest {
    private lateinit var eventPublisher: EventPublisher<TeamCreatedEvent>
    private lateinit var teamRepository: TeamRepository
    private lateinit var teamCreator: TeamCreator
    private lateinit var createTeamCommandHandler: CreateTeamCommandHandler

    @BeforeEach
    fun setUp() {
        eventPublisher = mockk()
        teamRepository = mockk()
        teamCreator = TeamCreator(teamRepository, eventPublisher)
        createTeamCommandHandler = CreateTeamCommandHandler(teamCreator)

        coEvery { teamRepository.create(any()) } returns Unit
        coEvery { eventPublisher.publish(any<TeamCreatedEvent>()) } returns Unit
    }

    @Test
    fun `should create team and publish event when handle is called`() = runBlocking {
        // Given
        val teamId = UUID.randomUUID().toString()
        val organizationId = UUID.randomUUID().toString()
        val name = "Test Team"
        val command = CreateTeamCommand(
            teamId = teamId,
            name = name,
            organizationId = organizationId,
        )

        // When
        createTeamCommandHandler.handle(command)

        // Then
        coVerify(exactly = 1) {
            teamRepository.create(
                withArg {
                    assert(it.id.value.toString() == teamId)
                    assert(it.name == name)
                    assert(it.organizationId.value.toString() == organizationId)
                    assert(it.members.isEmpty())
                },
            )
        }
    }
}
