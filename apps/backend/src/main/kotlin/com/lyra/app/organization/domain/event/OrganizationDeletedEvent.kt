package com.lyra.app.organization.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * Event that represents an organization being deleted.
 *
 * @property organizationId The id of the organization that was deleted.
 */
data class OrganizationDeletedEvent(
    val organizationId: String
) : BaseDomainEvent()
