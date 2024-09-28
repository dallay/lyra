package com.lyra.app.newsletter.tag.application.update

import com.lyra.app.newsletter.tag.domain.TagColor
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import java.util.UUID
import org.slf4j.LoggerFactory

/**
 *
 * @created 22/9/24
 */
@Service
class UpdateTagCommandHandler(private val tagUpdater: TagUpdater) : CommandHandler<UpdateTagCommand> {
    override suspend fun handle(command: UpdateTagCommand) {
        log.debug(
            "Updating tag with id {} and name {} for organization {}",
            command.id,
            command.name,
            command.organizationId,
        )
        val tagId = UUID.fromString(command.id)
        val color = command.color?.let { TagColor.fromString(it) }
        val organizationId = UUID.fromString(command.organizationId)
        tagUpdater.update(tagId, command.name, color, organizationId, command.subscribers)
    }

    companion object {
        private val log = LoggerFactory.getLogger(UpdateTagCommandHandler::class.java)
    }
}
