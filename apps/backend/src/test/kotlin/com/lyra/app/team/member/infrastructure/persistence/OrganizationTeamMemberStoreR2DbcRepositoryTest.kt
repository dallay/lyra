package com.lyra.app.team.member.infrastructure.persistence

import com.lyra.UnitTest
import com.lyra.app.team.member.infrastructure.persistence.entity.OrganizationTeamMemberEntity
import com.lyra.app.team.member.infrastructure.persistence.repository.OrganizationTeamMemberR2DbcRepository
import com.lyra.app.users.domain.UserId
import java.util.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.runBlocking
import net.datafaker.Faker
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@UnitTest
internal class OrganizationTeamMemberStoreR2DbcRepositoryTest {
    private val userId = UUID.randomUUID()
    private val faker = Faker()
    private val mockRepository = mock(OrganizationTeamMemberR2DbcRepository::class.java)
    private val repository = OrganizationTeamMemberStoreR2DbcRepository(mockRepository)

    @BeforeEach
    fun setUp() {
        reset(mockRepository)
    }

    @Test
    fun findAllTeamMembersReturnsAllMembers(): Unit = runBlocking {
        val userId = UserId(userId)
        val teamMembers = listOf(
            OrganizationTeamMemberEntity(
                teamName = faker.team().name(),
                organizationName = faker.company().name(),
                teamMemberRole = "OWNER",
                organizationId = UUID.randomUUID(),
                organizationOwnerId = this@OrganizationTeamMemberStoreR2DbcRepositoryTest.userId,
                teamId = UUID.randomUUID(),
                userId = this@OrganizationTeamMemberStoreR2DbcRepositoryTest.userId,
            ),
            OrganizationTeamMemberEntity(
                teamName = faker.team().name(),
                organizationName = faker.company().name(),
                teamMemberRole = "EDITOR",
                organizationId = UUID.randomUUID(),
                organizationOwnerId = UUID.randomUUID(),
                teamId = UUID.randomUUID(),
                userId = this@OrganizationTeamMemberStoreR2DbcRepositoryTest.userId,
            ),
        )
        `when`(mockRepository.findAllOrganizationTeamMembers(userId.value)).thenReturn(teamMembers.asFlow())

        val result = repository.findAllTeamMembers(userId)

        assertEquals(2, result.data.size)
        verify(mockRepository, times(1)).findAllOrganizationTeamMembers(userId.value)
    }

    @Test
    fun findAllTeamMembersReturnsEmptyListWhenNoMembers(): Unit = runBlocking {
        val userId = UserId(userId)
        `when`(mockRepository.findAllOrganizationTeamMembers(userId.value)).thenReturn(emptyFlow())

        val result = repository.findAllTeamMembers(userId)

        assertTrue(result.data.isEmpty())
        verify(mockRepository, times(1)).findAllOrganizationTeamMembers(userId.value)
    }

    @Test
    fun findAllTeamMembersHandlesException(): Unit = runBlocking {
        val userId = UserId(userId)
        `when`(mockRepository.findAllOrganizationTeamMembers(userId.value)).thenThrow(
            RuntimeException("Database error"),
        )

        assertThrows<RuntimeException> {
            runBlocking { repository.findAllTeamMembers(userId) }
        }
        verify(mockRepository, times(1)).findAllOrganizationTeamMembers(userId.value)
    }
}
