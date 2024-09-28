package com.lyra.app.newsletter.tag.application.delete

import com.lyra.common.domain.bus.command.Command

/**
 * Command class for deleting a tag.
 *
 * @property organizationId The ID of the organization to which the tag belongs.
 * @property tagId The ID of the tag to be deleted.
 */
data class DeleteTagCommand(
    val organizationId: String,
    val tagId: String
) : Command
