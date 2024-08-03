package com.lyra.app.team.application.delete

import com.lyra.UnitTest
import com.lyra.app.team.domain.TeamRemoverRepository
import com.lyra.app.team.domain.event.TeamRemovedEvent
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class DeleteTeamCommandHandlerTest {
    private lateinit var eventPublisher: EventPublisher<TeamRemovedEvent>
    private lateinit var repository: TeamRemoverRepository
    private lateinit var teamDeleter: TeamDeleter
    private lateinit var deleteTeamCommandHandler: DeleteTeamCommandHandler
    private lateinit var teamId: String

    @BeforeEach
    fun setUp() {
        eventPublisher = mockk()
        repository = mockk()
        teamDeleter = TeamDeleter(repository, eventPublisher)
        deleteTeamCommandHandler = DeleteTeamCommandHandler(teamDeleter)
        teamId = UUID.randomUUID().toString()

        coEvery { repository.delete(any()) } returns Unit
        coEvery { eventPublisher.publish(any(TeamRemovedEvent::class)) } returns Unit
    }

    @Test
    fun `should delete a team`() = runBlocking {
        // Given
        val command = DeleteTeamCommand(teamId = teamId)

        // When
        deleteTeamCommandHandler.handle(command)

        // Then
        coVerify(exactly = 1) {
            repository.delete(
                withArg {
                    assert(it.value.toString() == teamId)
                },
            )
        }
        coVerify(exactly = 1) { eventPublisher.publish(ofType<TeamRemovedEvent>()) }
    }
}
