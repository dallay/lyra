package com.lyra.app.notifications.application

import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

/**
 *
 * @created 12/1/24
 */
@Service
class SendNewContentNewsletterCommandHandler(
    private val sender: NewContentNewsletterSender
) : CommandHandler<SendNewContentNewsletterCommand> {
    override suspend fun handle(command: SendNewContentNewsletterCommand) {
        log.info("Handling command: $command")
        sender.sendNewContentNewsletter()
    }

    companion object {
        private val log = LoggerFactory.getLogger(SendNewContentNewsletterCommandHandler::class.java)
    }
}
