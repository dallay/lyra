package com.lyra.app.newsletter.application

import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import java.util.*
import org.slf4j.LoggerFactory

/**
 * Service class responsible for handling the SubscribeNewsletterCommand.
 *
 * @property subscriberRegistrator The service responsible for registering subscribers.
 */
@Service
class CreateSubscribeNewsletterCommandHandler(
    private val subscriberRegistrator: SubscriberRegistrator
) : CommandHandler<SubscribeNewsletterCommand> {
    /**
     * Function to handle the SubscribeNewsletterCommand.
     *
     * @param command The command to be handled.
     */
    override suspend fun handle(command: SubscribeNewsletterCommand) {
        log.debug("Handling command: {}", command)
        val id = UUID.fromString(command.id)
        val organizationId = UUID.fromString(command.organizationId)
        subscriberRegistrator.register(id, command.email, command.firstname, command.lastname, organizationId)
    }

    companion object {
        private val log = LoggerFactory.getLogger(CreateSubscribeNewsletterCommandHandler::class.java)
    }
}
