package com.lyra.app.users.infrastructure.event.consumer

import com.lyra.app.users.domain.event.UserCreatedEvent
import com.lyra.app.users.infrastructure.persistence.keycloak.KeycloakRepository
import com.lyra.common.domain.bus.event.EventConsumer
import com.lyra.common.domain.bus.event.Subscribe
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * Represents a class responsible for sending an email verification to a newly created user.
 *
 * This class is a component and an event consumer, subscribing to events of type UserCreatedEvent.
 * It uses a KeycloakRepository to perform the email verification process.
 *
 * @created 7/7/23
 * @param keycloakRepository the KeycloakRepository to use for performing the email verification process
 * @see KeycloakRepository for more information about the KeycloakRepository
 * @see Subscribe for more information about the Subscribe annotation
 * @see EventConsumer for more information about the EventConsumer interface
 * @see UserCreatedEvent for more information about the UserCreatedEvent
 */
@Component
@Subscribe(filterBy = UserCreatedEvent::class)
class SendEmailVerification(private val keycloakRepository: KeycloakRepository) :
    EventConsumer<UserCreatedEvent> {
    /**
     * Sends email verification to the user with the given user ID.
     *
     * @param event The UserCreatedEvent object representing the user creation event.
     */
    override suspend fun consume(event: UserCreatedEvent) {
        log.info("Sending email verification to user with id: {}", event.userId)
        keycloakRepository.verify(event.userId)
    }
    companion object {
        private val log = LoggerFactory.getLogger(SendEmailVerification::class.java)
    }
}
