package com.lyra.app.newsletter.application

import com.lyra.common.domain.bus.command.Command

/**
 * Data class representing the command to subscribe to a newsletter.
 *
 * @property id The unique identifier of the subscriber.
 * @property email The email address of the subscriber.
 * @property firstname The first name of the subscriber.
 * @property lastname The last name of the subscriber. This can be null.
 * @property organizationId The identifier of the organization the subscriber belongs to.
 */
data class SubscribeNewsletterCommand(
    val id: String,
    val email: String,
    val firstname: String?,
    val lastname: String?,
    val organizationId: String
) : Command
