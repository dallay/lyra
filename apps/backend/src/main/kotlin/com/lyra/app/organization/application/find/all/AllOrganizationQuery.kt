package com.lyra.app.organization.application.find.all

import com.lyra.app.organization.application.OrganizationResponses
import com.lyra.common.domain.bus.query.Query

/**
 * This class represents a query to find all organizations.
 *
 * @property userId The unique identifier of the user.
 */
data class AllOrganizationQuery(val userId: String) : Query<OrganizationResponses>
