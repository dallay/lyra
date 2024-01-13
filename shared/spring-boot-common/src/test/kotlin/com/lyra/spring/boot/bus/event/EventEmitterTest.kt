package com.lyra.spring.boot.bus.event

import com.lyra.common.domain.bus.event.DomainEvent
import com.lyra.common.domain.bus.event.EventConsumer
import com.lyra.common.domain.bus.event.EventFilter
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EventEmitterTest {

    private lateinit var eventEmitter: EventEmitter<DomainEvent>
    private val mockFilter = mockk<EventFilter<DomainEvent>>()
    private val mockConsumer = mockk<EventConsumer<DomainEvent>>(relaxed = true)
    private val mockEvent = mockk<DomainEvent>()

    @BeforeEach
    fun setUp() {
        eventEmitter = EventEmitter()
    }

    @Test
    fun `test on method`() {
        // Act
        eventEmitter.on(mockFilter, mockConsumer)

        // Assert
        // No direct way to verify as eventMultiplexer is private
        // The verification will be done in the `publish` method test
    }

    @Test
    fun `test publish method`() = runBlocking {
        // Arrange
        coEvery { mockConsumer.consume(mockEvent) } returns Unit
        coEvery { mockFilter.filter(any()) } returns true

        // Register the EventConsumer with the EventEmitter
        eventEmitter.on(mockFilter, mockConsumer)

        // Act
        eventEmitter.publish(mockEvent)

        // Assert
        coVerify { mockConsumer.consume(mockEvent) }
    }
}
