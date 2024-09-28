package com.lyra.app.newsletter.tag.application.delete

import com.lyra.app.newsletter.tag.domain.TagId
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

/**
 * Command handler for deleting a tag.
 *
 * @property tagDestroyer The service responsible for deleting tags.
 */
@Service
class DeleteTagCommandHandler(private val tagDestroyer: TagDestroyer) :
    CommandHandler<DeleteTagCommand> {

    /**
     * Handles the delete tag command.
     *
     * @param command The command containing the organization ID and tag ID.
     */
    override suspend fun handle(command: DeleteTagCommand) {
        val organizationId = OrganizationId(command.organizationId)
        val tagId = TagId(command.tagId)
        log.debug(
            "Deleting tag with tagId {} for organization {}",
            tagId.value,
            organizationId.value,
        )
        tagDestroyer.delete(organizationId, tagId)
    }

    companion object {
        private val log = LoggerFactory.getLogger(DeleteTagCommandHandler::class.java)
    }
}
