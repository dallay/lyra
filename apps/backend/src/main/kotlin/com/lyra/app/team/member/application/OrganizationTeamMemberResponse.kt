package com.lyra.app.team.member.application

import com.lyra.common.domain.bus.query.Response

/**
 * Data transfer object representing a response for an organization team member.
 *
 * @property teamId The ID of the team.
 * @property userId The ID of the user.
 * @property organizationId The ID of the organization.
 * @property organizationOwnerId The ID of the organization owner.
 * @property role The role of the user in the team.
 * @property teamName The name of the team.
 * @property organizationName The name of the organization.
 */
data class OrganizationTeamMemberResponse(
    val teamId: String,
    val userId: String,
    val organizationId: String,
    val organizationOwnerId: String,
    val role: String,
    val teamName: String,
    val organizationName: String
) : Response

/**
 * Data transfer object representing a list of organization team member responses.
 *
 * @property data The list of `OrganizationTeamMemberResponse` objects.
 */
data class OrganizationTeamMemberResponses(val data: List<OrganizationTeamMemberResponse>) : Response
