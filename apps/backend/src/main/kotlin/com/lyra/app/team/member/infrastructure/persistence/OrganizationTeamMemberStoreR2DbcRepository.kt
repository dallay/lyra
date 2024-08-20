package com.lyra.app.team.member.infrastructure.persistence

import com.lyra.app.team.member.application.OrganizationTeamMemberResponse
import com.lyra.app.team.member.application.OrganizationTeamMemberResponses
import com.lyra.app.team.member.domain.TeamMemberFinderRepository
import com.lyra.app.team.member.infrastructure.persistence.entity.OrganizationTeamMemberEntity
import com.lyra.app.team.member.infrastructure.persistence.repository.OrganizationTeamMemberR2DbcRepository
import com.lyra.app.users.domain.UserId
import kotlinx.coroutines.flow.toList
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class OrganizationTeamMemberStoreR2DbcRepository(
    private val organizationTeamMemberR2DbcRepository: OrganizationTeamMemberR2DbcRepository
) : TeamMemberFinderRepository {

    /**
     * Find all team members.
     * @return A list of all team members.
     */
    override suspend fun findAllTeamMembers(userId: UserId): OrganizationTeamMemberResponses {
        log.debug("Finding all organization team members")
        val allOrganizationTeamMembers: List<OrganizationTeamMemberEntity> =
            organizationTeamMemberR2DbcRepository.findAllOrganizationTeamMembers(userId.value)
                .toList()
        log.debug("Found {} team members", allOrganizationTeamMembers.size)
        log.debug("Team members: {}", allOrganizationTeamMembers)
        return OrganizationTeamMemberResponses(allOrganizationTeamMembers.map { it.toResponse() })
    }

    private fun OrganizationTeamMemberEntity.toResponse(): OrganizationTeamMemberResponse =
        OrganizationTeamMemberResponse(
            teamId = teamId.toString(),
            userId = userId.toString(),
            organizationId = organizationId.toString(),
            organizationOwnerId = organizationOwnerId.toString(),
            role = teamMemberRole,
            teamName = teamName,
            organizationName = organizationName,
        ).also { log.debug("Mapping team member to response: {}", it) }

    companion object {
        private val log = LoggerFactory.getLogger(OrganizationTeamMemberStoreR2DbcRepository::class.java)
    }
}
