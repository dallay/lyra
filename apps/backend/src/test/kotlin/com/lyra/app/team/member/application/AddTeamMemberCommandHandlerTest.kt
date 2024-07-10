package com.lyra.app.team.member.application

import com.lyra.UnitTest
import com.lyra.app.team.member.domain.TeamMemberRepository
import com.lyra.app.team.member.domain.TeamMemberRole
import com.lyra.app.team.member.domain.event.TeamMemberAddedEvent
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class AddTeamMemberCommandHandlerTest {
    private lateinit var eventPublisher: EventPublisher<TeamMemberAddedEvent>
    private lateinit var teamMemberRepository: TeamMemberRepository
    private lateinit var teamMemberCreator: TeamMemberCreator
    private lateinit var addTeamMemberCommandHandler: AddTeamMemberCommandHandler

    @BeforeEach
    fun setUp() {
        eventPublisher = mockk()
        teamMemberRepository = mockk()
        teamMemberCreator = TeamMemberCreator(teamMemberRepository, eventPublisher)
        addTeamMemberCommandHandler = AddTeamMemberCommandHandler(teamMemberCreator)

        coEvery { teamMemberRepository.create(any()) } returns Unit
        coEvery { eventPublisher.publish(any<TeamMemberAddedEvent>()) } returns Unit
    }

    @Test
    fun `should add a team member and publish event when handle is called`() = runBlocking {
        // Given
        val teamId = UUID.randomUUID().toString()
        val userId = UUID.randomUUID().toString()
        val role: TeamMemberRole = TeamMemberRole.EDITOR
        val command = AddTeamMemberCommand(
            teamId = teamId,
            userId = userId,
            role = role.name,
        )
        // When
        addTeamMemberCommandHandler.handle(command)
        // Then
        coVerify(exactly = 1) {
            teamMemberRepository.create(
                withArg {
                    assert(it.id.value.first.toString() == teamId)
                    assert(it.id.value.second.toString() == userId)
                    assert(it.role == role)
                },
            )
        }
    }
}
