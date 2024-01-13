package com.lyra.app.notifications.application

import com.lyra.app.newsletter.SubscriberStub
import com.lyra.app.newsletter.application.SubscriberResponse
import com.lyra.app.newsletter.application.SubscribersResponse
import com.lyra.app.newsletter.application.search.active.SearchAllActiveSubscribersQuery
import com.lyra.app.notifications.domain.EmailSender
import com.lyra.app.notifications.domain.event.NewContentNewsletterEmailSent
import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SendNewContentNewsletterCommandHandlerTest {
    private var eventPublisher: EventPublisher<NewContentNewsletterEmailSent> = mockk()
    private var emailSender: EmailSender = mockk()
    private var mediator: Mediator = mockk()
    private val newContentNewsletterSender = NewContentNewsletterSender(
        emailSender,
        mediator,
        eventPublisher,
    )
    private val sendNewContentNewsletterCommandHandler = SendNewContentNewsletterCommandHandler(
        newContentNewsletterSender,
    )
    @BeforeEach
    fun setUp() {
        coEvery { mediator.send(any(SearchAllActiveSubscribersQuery::class)) } returns subscribersResponse()
        coEvery { eventPublisher.publish(any(NewContentNewsletterEmailSent::class)) } returns Unit
        coEvery { emailSender.send(any()) } returns Unit
    }

    @Test
    fun `should send new content newsletter`() = runBlocking {
        // Given
        val command = SendNewContentNewsletterCommand()
        // When
        sendNewContentNewsletterCommandHandler.handle(command)
        // Then
        coVerify(exactly = 1) { mediator.send(any(SearchAllActiveSubscribersQuery::class)) }
        coVerify(exactly = 10) { emailSender.send(any()) }
        coVerify(exactly = 10) { eventPublisher.publish(any(NewContentNewsletterEmailSent::class)) }
    }

    private fun subscribersResponse(): SubscribersResponse {
        return SubscriberStub.dummyRandomSubscribersList().toList()
            .map {
                SubscriberResponse(
                    id = it.id.value.toString(),
                    email = it.email.value,
                    name = it.name.fullName(),
                    status = it.status.name,
                )
            }.let {
                SubscribersResponse(it)
            }
    }
}
