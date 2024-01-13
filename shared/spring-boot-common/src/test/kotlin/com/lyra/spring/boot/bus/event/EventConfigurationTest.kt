package com.lyra.spring.boot.bus.event

import com.lyra.common.domain.bus.event.DomainEvent
import com.lyra.common.domain.bus.event.EventConsumer
import com.lyra.common.domain.bus.event.Subscribe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationContext

@Subscribe(filterBy = TestEventConsumer::class)
class TestEventConsumer : EventConsumer<DomainEvent> {
    override suspend fun consume(event: DomainEvent) {
        // Do nothing
        println("\uD83D\uDFE2 Event consumed")
    }
}
class EventConfigurationTest {

    private lateinit var eventConfiguration: EventConfiguration
    private lateinit var applicationContext: ApplicationContext

    @BeforeEach
    fun setUp() {
        applicationContext = mockk()
        eventConfiguration = EventConfiguration(applicationContext)

        // Configurar el mock de ApplicationContext para devolver una instancia de TestEventConsumer
        every { applicationContext.getBean(TestEventConsumer::class.java) } returns TestEventConsumer()
    }

    @Test
    fun `test config event emitter`() {
        // Arrange
        val mockEventEmitter = mockk<EventEmitter<DomainEvent>>(relaxed = true)

        every { applicationContext.getBeansOfType(EventConsumer::class.java) } returns
            dummyBeansImplementingEventConsumer()

        // Act
        eventConfiguration.configEventEmitter(mockEventEmitter)

        // Assert
        verify { mockEventEmitter.on(any(), any()) }
    }

    private fun dummyBeansImplementingEventConsumer(): Map<String, EventConsumer<*>> {
        // Crear una instancia de TestEventConsumer
        val testEventConsumer = TestEventConsumer()

        // Devolver la instancia de TestEventConsumer
        return mapOf("testEventConsumer" to testEventConsumer)
    }
}
