package com.lyra.app.team.member.application.remove

import com.lyra.UnitTest
import com.lyra.app.team.domain.TeamId
import com.lyra.app.team.member.domain.TeamMemberRemoverRepository
import com.lyra.app.team.member.domain.event.TeamMemberRemovedEvent
import com.lyra.app.users.domain.UserId
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class RemoveTeamMemberCommandHandlerTest {
    private lateinit var eventPublisher: EventPublisher<TeamMemberRemovedEvent>
    private lateinit var teamMemberRemoverRepository: TeamMemberRemoverRepository
    private lateinit var teamMemberRemover: TeamMemberRemover
    private lateinit var removeTeamMemberCommandHandler: RemoveTeamMemberCommandHandler
    @BeforeEach
    fun setUp() {
        eventPublisher = mockk()
        teamMemberRemoverRepository = mockk()
        teamMemberRemover = TeamMemberRemover(teamMemberRemoverRepository, eventPublisher)
        removeTeamMemberCommandHandler = RemoveTeamMemberCommandHandler(teamMemberRemover)

        coEvery { teamMemberRemoverRepository.remove(any<TeamId>(), any<UserId>()) } returns Unit
        coEvery { eventPublisher.publish(any<TeamMemberRemovedEvent>()) } returns Unit
    }

    @Test
    fun `should remove a team member and publish event when handle is called`(): Unit = runBlocking {
        // Given
        val teamId = TeamId.create()
        val userId = UserId.create()
        val command = RemoveTeamMemberCommand(
            teamId = teamId.value.toString(),
            userId = userId.value.toString(),
        )
        // When
        removeTeamMemberCommandHandler.handle(command)
        // Then
        coVerify(exactly = 1) {
            teamMemberRemoverRepository.remove(
                withArg {
                    assert(it.value.toString() == teamId.value.toString())
                },
                withArg {
                    assert(it.value.toString() == userId.value.toString())
                },
            )
        }
    }
}
