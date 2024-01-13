package com.lyra.common.domain.bus.event

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class EventMultiplexerTest {

    @Test
    fun `should consume event when filter passes`() = runBlocking {
        // Arrange
        val eventMultiplexer = EventMultiplexer<DomainEvent>()
        val mockEventConsumer: EventConsumer<DomainEvent> = mockk(relaxed = true)
        val mockEventFilter: EventFilter<DomainEvent> = mockk {
            coEvery { filter(any()) } returns true
        }
        val mockEvent: DomainEvent = mockk()

        // Act
        eventMultiplexer.on(mockEventFilter, mockEventConsumer)
        eventMultiplexer.consume(mockEvent)

        // Assert
        coVerify { mockEventConsumer.consume(mockEvent) }
    }

    @Test
    fun `should not consume event when filter fails`() = runBlocking {
        // Arrange
        val eventMultiplexer = EventMultiplexer<DomainEvent>()
        val mockEventConsumer: EventConsumer<DomainEvent> = mockk(relaxed = true)
        val mockEventFilter: EventFilter<DomainEvent> = mockk {
            coEvery { filter(any()) } returns false
        }
        val mockEvent: DomainEvent = mockk()

        // Act
        eventMultiplexer.on(mockEventFilter, mockEventConsumer)
        eventMultiplexer.consume(mockEvent)

        // Assert
        coVerify(exactly = 0) { mockEventConsumer.consume(mockEvent) }
    }
}
