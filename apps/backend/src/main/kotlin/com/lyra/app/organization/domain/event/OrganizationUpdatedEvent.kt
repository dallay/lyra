package com.lyra.app.organization.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * OrganizationUpdatedEvent is a data class representing the event of an organization being updated.
 * It extends BaseDomainEvent.
 *
 * @property id The unique identifier of the organization that was updated.
 * @property organizationName The new name of the organization after the update.
 * @property userId The unique identifier of the user who updated the organization.
 */
data class OrganizationUpdatedEvent(
    val id: String,
    val organizationName: String,
    val userId: String
) : BaseDomainEvent()
