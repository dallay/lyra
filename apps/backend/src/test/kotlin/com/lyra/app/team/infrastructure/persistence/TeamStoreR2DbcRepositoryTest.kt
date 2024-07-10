package com.lyra.app.team.infrastructure.persistence

import com.lyra.UnitTest
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.app.team.TeamStub
import com.lyra.app.team.domain.Team
import com.lyra.app.team.domain.TeamId
import com.lyra.app.team.domain.exception.TeamException
import com.lyra.app.team.infrastructure.persistence.mapper.TeamMapper.toDomain
import com.lyra.app.team.infrastructure.persistence.mapper.TeamMapper.toEntity
import com.lyra.app.team.infrastructure.persistence.repository.TeamR2dbcRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import java.util.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.TransientDataAccessResourceException

@UnitTest
internal class TeamStoreR2DbcRepositoryTest {

    private lateinit var teamR2dbcRepository: TeamR2dbcRepository
    private lateinit var teamStoreR2DbcRepository: TeamStoreR2DbcRepository
    private lateinit var team: Team

    @BeforeEach
    fun setUp() {
        teamR2dbcRepository = mockk()
        teamStoreR2DbcRepository = TeamStoreR2DbcRepository(teamR2dbcRepository)
        team = TeamStub.create()
    }

    @Test
    fun `should create a new team`() = runBlocking {
        val teamEntity = team.toEntity()

        coEvery { teamR2dbcRepository.save(teamEntity) } returns teamEntity

        teamStoreR2DbcRepository.create(team)

        coVerify { teamR2dbcRepository.save(teamEntity) }
    }

    @Test
    fun `should throw TeamException when creating a team that already exists`() = runBlocking {
        val teamEntity = team.toEntity()

        coEvery { teamR2dbcRepository.save(teamEntity) } throws DuplicateKeyException("")

        assertThrows<TeamException> {
            teamStoreR2DbcRepository.create(team)
        }

        coVerify { teamR2dbcRepository.save(teamEntity) }
    }

    @Test
    fun `should update an existing team`() = runBlocking {
        val teamEntity = team.toEntity()

        coEvery { teamR2dbcRepository.save(teamEntity) } returns teamEntity

        teamStoreR2DbcRepository.update(team)

        coVerify { teamR2dbcRepository.save(teamEntity) }
    }

    @Test
    fun `should throw TeamException when updating a team that does not exist`() = runBlocking {
        val teamEntity = team.toEntity()

        coEvery { teamR2dbcRepository.save(teamEntity) } throws TransientDataAccessResourceException(
            "",
        )

        assertThrows<TeamException> {
            teamStoreR2DbcRepository.update(team)
        }

        coVerify { teamR2dbcRepository.save(teamEntity) }
    }

    @Test
    fun `should find a team by its id`() = runBlocking {
        val teamEntity = team.toEntity()

        coEvery { teamR2dbcRepository.findById(team.id.value) } returns teamEntity

        val foundTeam = teamStoreR2DbcRepository.findById(team.id)

        assertEquals(team, foundTeam)
        coVerify { teamR2dbcRepository.findById(team.id.value) }
    }

    @Test
    fun `should return null if team not found by id`() = runBlocking {
        coEvery { teamR2dbcRepository.findById(team.id.value) } returns null

        val foundTeam = teamStoreR2DbcRepository.findById(team.id)

        assertNull(foundTeam)
        coVerify { teamR2dbcRepository.findById(team.id.value) }
    }

    @Test
    fun `should find all teams that belong to an organization`() = runBlocking {
        val organizationId = OrganizationId(UUID.randomUUID())
        val teamEntities = listOf(team.toEntity())
        val teams = teamEntities.map { it.toDomain() }

        coEvery { teamR2dbcRepository.findAllByOrganizationId(organizationId.value) } returns teamEntities.asFlow()

        val foundTeams =
            teamStoreR2DbcRepository.findAllTeamsThatBelongToOrganization(organizationId)

        assertEquals(teams, foundTeams)
        coVerify { teamR2dbcRepository.findAllByOrganizationId(organizationId.value) }
    }

    @Test
    fun `should delete a team by its id`() = runBlocking {
        val teamId = TeamId(UUID.randomUUID())
        coEvery { teamR2dbcRepository.deleteById(teamId.value) } just Runs

        teamStoreR2DbcRepository.delete(teamId)

        coVerify { teamR2dbcRepository.deleteById(teamId.value) }
    }
}
