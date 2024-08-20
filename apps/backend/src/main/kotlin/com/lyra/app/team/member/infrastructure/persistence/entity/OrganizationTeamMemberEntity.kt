package com.lyra.app.team.member.infrastructure.persistence.entity

import java.util.*

/**
 * Projection interface for organization team members.
 *
 * @property teamName The name of the team.
 * @property organizationName The name of the organization.
 * @property teamMemberRole The role of the team member.
 * @property organizationId The ID of the organization.
 * @property organizationOwnerId The ID of the organization owner.
 * @property teamId The ID of the team.
 * @property userId The ID of the user.
 * @created 18/8/24
 */
data class OrganizationTeamMemberEntity(
    val teamName: String,
    val organizationName: String,
    val teamMemberRole: String,
    val organizationId: UUID,
    val organizationOwnerId: UUID,
    val teamId: UUID,
    val userId: UUID
)
