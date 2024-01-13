package com.lyra.app.notifications.infrastructure

import com.lyra.app.notifications.domain.EmailMessage
import com.lyra.app.notifications.domain.EmailSender
import com.lyra.common.domain.Service
import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import java.io.IOException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value

@Service
class SendgridEmailSender(
    @Value("\${notification.email.sendgrid-api-key}")
    private val apiKey: String,
    private val sg: SendGrid = SendGrid(apiKey)
) : EmailSender {
    override suspend fun send(emailMessage: EmailMessage) {
        log.info("Sending email to ${emailMessage.to.value}")
        val from = Email(emailMessage.from.value)
        val to = Email(emailMessage.to.value)
        val content = Content("text/plain", emailMessage.body)
        val mail = Mail(from, emailMessage.subject, to, content)
        val request = Request()
        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()
            val response = sg.api(request)
            log.info("Email sent to ${emailMessage.to.value} with status code ${response.statusCode}")
        } catch (ex: IOException) {
            log.error("Error sending email to ${emailMessage.to.value}", ex)
        }
    }
    companion object {
        private val log = LoggerFactory.getLogger(SendgridEmailSender::class.java)
    }
}
