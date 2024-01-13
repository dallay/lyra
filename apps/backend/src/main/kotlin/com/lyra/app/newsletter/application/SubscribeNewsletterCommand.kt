package com.lyra.app.newsletter.application

import com.lyra.common.domain.bus.command.Command

/**
 *
 * @created 7/1/24
 */
data class SubscribeNewsletterCommand(
    val id: String,
    val email: String,
    val firstname: String,
    val lastname: String?
) : Command
