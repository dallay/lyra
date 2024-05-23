package com.lyra.app.notifications.infrastructure

import com.lyra.app.notifications.domain.EmailMessage
import com.lyra.app.notifications.domain.EmailMessageId
import com.lyra.common.domain.vo.email.Email
import com.sendgrid.Request
import com.sendgrid.Response
import com.sendgrid.SendGrid
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.io.IOException
import java.util.*
import kotlinx.coroutines.runBlocking
import net.datafaker.Faker
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SendgridEmailSenderTest {
    private val faker = Faker()
    private val sg: SendGrid = mockk()
    private lateinit var sendgridEmailSender: SendgridEmailSender

    @BeforeEach
    fun setUp() {
        val response: Response = mockk()
        val request: Request = mockk()
        sendgridEmailSender = SendgridEmailSender("SOME_API_KEY", sg)
        every { sg.api(any(Request::class)) } returns response
        every { response.statusCode } returns 200
        every { request.method = any() } returns Unit
    }

    @Test
    fun `send email`() = runBlocking {
        val emailMessage = ExampleEmailMessage(
            id = EmailMessageId(UUID.randomUUID().toString()),
            from = Email(faker.internet().emailAddress()),
            to = Email(faker.internet().emailAddress()),
            subject = faker.lorem().sentence(),
            body = faker.lorem().paragraph(),
        )
        sendgridEmailSender.send(emailMessage)
        verify(exactly = 1) { sg.api(any()) }
    }

    @Test
    fun `send email with error`() = runBlocking {
        val emailMessage = ExampleEmailMessage(
            id = EmailMessageId(UUID.randomUUID().toString()),
            from = Email(faker.internet().emailAddress()),
            to = Email(faker.internet().emailAddress()),
            subject = faker.lorem().sentence(),
            body = faker.lorem().paragraph(),
        )
        every { sg.api(any()) } throws IOException("Error sending email")
        sendgridEmailSender.send(emailMessage)
        verify(exactly = 1) { sg.api(any()) }
    }
}

class ExampleEmailMessage(
    override val id: EmailMessageId,
    from: Email,
    to: Email,
    subject: String,
    body: String
) : EmailMessage(id, from, to, subject, body)
