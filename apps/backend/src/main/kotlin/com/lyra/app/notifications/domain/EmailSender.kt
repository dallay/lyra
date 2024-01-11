package com.lyra.app.notifications.domain

/**
 *
 * @created 9/1/24
 */
interface EmailSender {
    suspend fun send(emailMessage: EmailMessage)
}
