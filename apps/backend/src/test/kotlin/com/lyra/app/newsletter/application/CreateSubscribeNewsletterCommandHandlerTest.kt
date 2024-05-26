package com.lyra.app.newsletter.application

import com.lyra.UnitTest
import com.lyra.app.newsletter.domain.Subscriber
import com.lyra.app.newsletter.domain.SubscriberRepository
import com.lyra.app.newsletter.domain.event.SubscriberCreatedEvent
import com.lyra.common.domain.bus.event.EventPublisher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkClass
import java.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class CreateSubscribeNewsletterCommandHandlerTest {

    private var eventPublisher: EventPublisher<SubscriberCreatedEvent> = mockk()

    private var subscriberRepository = mockkClass(SubscriberRepository::class)

    private var subscriberRegistrator: SubscriberRegistrator = SubscriberRegistrator(
        subscriberRepository,
        eventPublisher,
    )

    private var createSubscribeNewsletterCommandHandler: CreateSubscribeNewsletterCommandHandler =
        CreateSubscribeNewsletterCommandHandler(subscriberRegistrator)
    private val email = "john.doe@lyra.com"
    private val firstname = "John"
    private val lastname = "Doe"

    @BeforeEach
    fun setUp() {
        coEvery { subscriberRepository.create(any(Subscriber::class)) } returns Unit
        coEvery { eventPublisher.publish(any(SubscriberCreatedEvent::class)) } returns Unit
    }

    @Test
    fun `should register a subscriber`() = runBlocking {
        // Given
        val command =
            SubscribeNewsletterCommand(UUID.randomUUID().toString(), email, firstname, lastname)
        // When
        createSubscribeNewsletterCommandHandler.handle(command)
        // Then
        coVerify(exactly = 1) { subscriberRepository.create(any(Subscriber::class)) }
        coVerify(exactly = 1) { eventPublisher.publish(any(SubscriberCreatedEvent::class)) }
    }
}
