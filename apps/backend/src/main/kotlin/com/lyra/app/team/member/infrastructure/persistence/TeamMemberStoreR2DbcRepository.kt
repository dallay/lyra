package com.lyra.app.team.member.infrastructure.persistence

import com.lyra.app.team.domain.TeamId
import com.lyra.app.team.member.domain.TeamMember
import com.lyra.app.team.member.domain.TeamMemberRemoverRepository
import com.lyra.app.team.member.domain.TeamMemberRepository
import com.lyra.app.team.member.domain.exception.TeamMemberException
import com.lyra.app.team.member.infrastructure.persistence.mapper.TeamMemberMapper.toEntity
import com.lyra.app.team.member.infrastructure.persistence.repository.TeamMemberR2dbcRepository
import com.lyra.app.users.domain.UserId
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Repository

@Repository
class TeamMemberStoreR2DbcRepository(
    private val teamMemberRepository: TeamMemberR2dbcRepository
) : TeamMemberRepository, TeamMemberRemoverRepository {
    /**
     * Create a new team member.
     *
     * @param member The team member to be created.
     */
    override suspend fun create(member: TeamMember) {
        val teamId = member.id.value.first
        val userId = member.id.value.second
        log.debug("Creating team member with teamId: {} and userId: {}", teamId, userId)
        try {
            teamMemberRepository.save(member.toEntity())
        } catch (e: DuplicateKeyException) {
            log.error("Team member already exists in the database: $teamId and $userId")
            throw TeamMemberException("Error creating team member", e)
        }
    }

    /**
     * Updates a team member.
     *
     * @param member The team member to be updated.
     */
    override suspend fun update(member: TeamMember) {
        log.debug("Updating team member with id: {}", member.id)
        teamMemberRepository.save(member.toEntity())
    }

    /**
     * Removes a team member. A team member is a user that belongs to a team.
     * @param teamId The unique identifier of the team to which the user belongs.
     * @param userId The unique identifier of the user to be removed.
     */
    override suspend fun remove(teamId: TeamId, userId: UserId) {
        log.debug("Removing member with id: {} from team with id: {}", userId, teamId)
        teamMemberRepository.delete(teamId.value, userId.value)
    }

    companion object {
        private val log = LoggerFactory.getLogger(TeamMemberStoreR2DbcRepository::class.java)
    }
}
