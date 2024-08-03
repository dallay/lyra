package com.lyra.app.team.application.find.all

import com.lyra.app.team.application.TeamResponses
import com.lyra.common.domain.bus.query.Query

/**
 * Query to find all teams of an organization.
 * @property organizationId The unique identifier of the organization.
 */
data class AllTeamQuery(val organizationId: String) : Query<TeamResponses>
