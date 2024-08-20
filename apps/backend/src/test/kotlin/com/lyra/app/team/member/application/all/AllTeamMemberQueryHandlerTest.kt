package com.lyra.app.team.member.application.all

import com.lyra.UnitTest
import com.lyra.app.team.member.application.OrganizationTeamMemberResponse
import com.lyra.app.team.member.application.OrganizationTeamMemberResponses
import com.lyra.app.team.member.domain.TeamMemberFinderRepository
import com.lyra.app.users.domain.UserId
import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.mockk
import java.util.*
import net.datafaker.Faker
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class AllTeamMemberQueryHandlerTest {
    private lateinit var repository: TeamMemberFinderRepository
    private lateinit var finder: AllTeamMemberFinder
    private lateinit var handler: AllTeamMemberQueryHandler
    private val faker = Faker()
    private val organizationId = UUID.randomUUID()
    private val organizationOwnerId = UUID.randomUUID()
    private val teamId = UUID.randomUUID()
    private lateinit var userId: UserId

    @BeforeEach
    fun setUp() {
        repository = mockk()
        finder = AllTeamMemberFinder(repository)
        handler = AllTeamMemberQueryHandler(finder)
        userId = UserId(organizationId.toString())
        coEvery { repository.findAllTeamMembers(userId) } returns OrganizationTeamMemberResponses(
            listOf(
                OrganizationTeamMemberResponse(
                    teamName = faker.team().name(),
                    organizationName = faker.company().name(),
                    role = "OWNER",
                    organizationId = organizationId.toString(),
                    organizationOwnerId = organizationOwnerId.toString(),
                    teamId = teamId.toString(),
                    userId = userId.value.toString(),
                ),
                OrganizationTeamMemberResponse(
                    teamName = faker.team().name(),
                    organizationName = faker.company().name(),
                    role = "EDITOR",
                    organizationId = UUID.randomUUID().toString(),
                    organizationOwnerId = UUID.randomUUID().toString(),
                    teamId = teamId.toString(),
                    userId = userId.value.toString(),
                ),
            ),
        )
    }

    @Test
    fun `should find all team members`(): Unit = runBlocking {
        // Given
        val query = GetAllTeamMember(userId.value.toString())

        // When
        val response = handler.handle(query)

        // Then
        assertEquals(2, response.data.size)
    }
}
