package com.lyra.app.newsletter.application

import com.lyra.app.newsletter.domain.FirstName
import com.lyra.app.newsletter.domain.LastName
import com.lyra.app.newsletter.domain.Name
import com.lyra.app.newsletter.domain.SubscriberId
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.command.CommandHandler
import com.lyra.common.domain.email.Email

@Service
class CreateSubscribeNewsletterCommandHandler(
    private val subscriberRegistrator: SubscriberRegistrator
) : CommandHandler<SubscribeNewsletterCommand> {
    override suspend fun handle(command: SubscribeNewsletterCommand) {
        val id = SubscriberId(command.id)
        val email = Email(command.email)
        val name = Name(FirstName(command.firstname), command.lastname?.let { LastName(it) })
        subscriberRegistrator.register(id, email, name)
    }
}
