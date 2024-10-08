package com.lyra.app.newsletter.tag.application

import com.lyra.app.newsletter.tag.domain.TagColor
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import java.util.UUID
import org.slf4j.LoggerFactory

/**
 * Command handler for creating a new tag.
 *
 * @property tagCreator The service responsible for creating tags.
 */
@Service
class CreateTagCommandHandler(
    private val tagCreator: TagCreator
) : CommandHandler<CreateTagCommand> {

    /**
     * Handles the CreateTagCommand to create a new tag.
     *
     * @param command The command containing the tag details.
     */
    override suspend fun handle(command: CreateTagCommand) {
        log.debug(
            "Creating tag with id {} and name {} for organization {}",
            command.id,
            command.name,
            command.organizationId,
        )
        val tagId = UUID.fromString(command.id)
        val color = TagColor.fromString(command.color)
        val organizationId = UUID.fromString(command.organizationId)
        tagCreator.create(tagId, command.name, color, organizationId, command.subscribers)
    }

    companion object {
        private val log = LoggerFactory.getLogger(CreateTagCommandHandler::class.java)
    }
}
