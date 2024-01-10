package com.lyra.app.notifications.domain

/**
 *
 * @created 9/1/24
 */
interface EmailSender {
    fun send(emailMessage: EmailMessage)
}
