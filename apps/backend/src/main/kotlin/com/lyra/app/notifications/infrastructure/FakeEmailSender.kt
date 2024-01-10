package com.lyra.app.notifications.infrastructure

import com.lyra.app.notifications.domain.EmailMessage
import com.lyra.app.notifications.domain.EmailSender
import com.lyra.common.domain.Service
import org.slf4j.LoggerFactory

/**
 *
 * @created 9/1/24
 */
@Service
class FakeEmailSender : EmailSender {
    override fun send(emailMessage: EmailMessage) {
        log.info("Sending email to ${emailMessage.to.value}")
    }
    companion object {
        private val log = LoggerFactory.getLogger(FakeEmailSender::class.java)
    }
}
