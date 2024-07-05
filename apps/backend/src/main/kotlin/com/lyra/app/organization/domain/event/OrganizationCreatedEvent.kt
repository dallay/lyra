package com.lyra.app.organization.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * OrganizationCreatedEvent is a data class representing the event of an organization being created.
 * It extends BaseDomainEvent.
 *
 * @property id The unique identifier of the organization that was created.
 * @property organizationName The name of the organization that was created.
 * @property userId The unique identifier of the user who created the organization.
 */
data class OrganizationCreatedEvent(
    val id: String,
    val organizationName: String,
    val userId: String
) :
    BaseDomainEvent()
