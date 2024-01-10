package com.lyra.app.notifications.application

import com.lyra.common.domain.bus.command.Command

/**
 *
 * @created 9/1/24
 */
data class VerifySubscriberNewsletterCommand(val id: String) : Command
