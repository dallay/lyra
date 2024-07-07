package com.lyra.app.team.infrastructure.persistence

import com.lyra.app.organization.domain.OrganizationId
import com.lyra.app.team.domain.Team
import com.lyra.app.team.domain.TeamFinderRepository
import com.lyra.app.team.domain.TeamId
import com.lyra.app.team.domain.TeamRemoverRepository
import com.lyra.app.team.domain.TeamRepository
import com.lyra.app.team.domain.exception.TeamException
import com.lyra.app.team.infrastructure.persistence.mapper.TeamMapper.toDomain
import com.lyra.app.team.infrastructure.persistence.mapper.TeamMapper.toEntity
import com.lyra.app.team.infrastructure.persistence.repository.TeamR2dbcRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Repository

@Repository
class TeamStoreR2DbcRepository(
    private val teamRepository: TeamR2dbcRepository
) : TeamRepository, TeamFinderRepository, TeamRemoverRepository {

    /**
     * Create a new team.
     *
     * @param team The team to be created.
     */
    override suspend fun create(team: Team) {
        log.debug("Creating team with id: {}", team.id)
        try {
            teamRepository.save(team.toEntity())
        } catch (e: DuplicateKeyException) {
            log.error("Team already exists in the database: ${team.id.value}")
            throw TeamException("Error creating team", e)
        }
    }

    /**
     * Updates a team.
     *
     * @param team The team to be updated.
     */
    override suspend fun update(team: Team) {
        log.debug("Updating team with id: {}", team.id)
        try {
            teamRepository.save(team.toEntity())
        } catch (e: DuplicateKeyException) {
            log.error("Team already exists in the database: {}", team.id.value)
            throw TeamException("Error updating team", e)
        } catch (e: org.springframework.dao.TransientDataAccessResourceException) {
            log.error("Error updating form with id: ${team.id.value}")
            throw TeamException("Error updating form because it does not exist", e)
        }
    }

    /**
     * Find a team by its unique identifier.
     *
     * @param id The unique identifier of the team.
     * @return The team with the given unique identifier.
     */
    override suspend fun findById(id: TeamId): Team? {
        log.debug("Finding team with id: {}", id)
        return teamRepository.findById(id.value)?.toDomain()
    }

    /**
     * Find all team that belong to an organization.
     * @param organizationId The unique identifier of the organization.
     *
     * @return The list of teams that belong to the organization.
     */
    override suspend fun findAllTeamsThatBelongToOrganization(organizationId: OrganizationId): List<Team> {
        log.debug("Finding all teams that belong to organization with id: {}", organizationId)
        return teamRepository.findAllByOrganizationId(organizationId.value)
            .map { it.toDomain() }
            .toList()
    }

    /**
     * Deletes a team.
     *
     * @param teamId The team id.
     */
    override suspend fun delete(teamId: TeamId) {
        log.debug("Deleting team with id: {}", teamId)
        teamRepository.deleteById(teamId.value)
    }

    companion object {
        private val log = LoggerFactory.getLogger(TeamStoreR2DbcRepository::class.java)
    }
}
