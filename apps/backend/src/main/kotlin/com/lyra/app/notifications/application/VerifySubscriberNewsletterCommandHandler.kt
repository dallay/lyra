package com.lyra.app.notifications.application

import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import org.slf4j.LoggerFactory

/**
 *
 * @created 9/1/24
 */
@Service
class VerifySubscriberNewsletterCommandHandler(
    private val sender: VerifySubscriberNewsletterSender,
) : CommandHandler<VerifySubscriberNewsletterCommand> {
    override suspend fun handle(command: VerifySubscriberNewsletterCommand) {
        log.info("Sending newsletter to subscriber ${command.id}")
        sender.send()
    }

    companion object {
        private val log = LoggerFactory.getLogger(VerifySubscriberNewsletterCommandHandler::class.java)
    }
}
