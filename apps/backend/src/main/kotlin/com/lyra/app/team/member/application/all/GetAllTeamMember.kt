package com.lyra.app.team.member.application.all

import com.lyra.app.team.member.application.OrganizationTeamMemberResponses
import com.lyra.common.domain.bus.query.Query

/**
 * Query to get all team members
 *
 * @property userId The ID of the user.
 */
data class GetAllTeamMember(val userId: String) : Query<OrganizationTeamMemberResponses>
