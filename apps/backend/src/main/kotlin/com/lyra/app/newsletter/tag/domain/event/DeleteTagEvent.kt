package com.lyra.app.newsletter.tag.domain.event

import com.lyra.common.domain.bus.event.BaseDomainEvent

/**
 * Event class representing the deletion of a tag.
 *
 * @property organizationId The ID of the organization to which the tag belongs.
 * @property tagId The ID of the tag that was deleted.
 */
data class DeleteTagEvent(
    val organizationId: String,
    val tagId: String
) : BaseDomainEvent()
