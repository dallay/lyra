package com.lyra.app.newsletter.infrastructure.persistence.converter

import com.lyra.app.newsletter.domain.SubscriberStatus
import io.mockk.every
import io.mockk.mockk
import io.r2dbc.spi.Row
import java.time.LocalDateTime
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SubscriberConverterTest {

    @Test
    fun `should convert Row to SubscriberEntity`() {
        val converter = SubscriberConverter()
        val mockRow: Row = mockk {
            every { get("id", UUID::class.java) } returns UUID.randomUUID()
            every { get("email", String::class.java) } returns "test@example.com"
            every { get("firstname", String::class.java) } returns "John"
            every { get("lastname", String::class.java) } returns "Doe"
            every { get("status", SubscriberStatus::class.java) } returns SubscriberStatus.ENABLED
            every { get("workspace_id", UUID::class.java) } returns UUID.randomUUID()
            every { get("created_at", LocalDateTime::class.java) } returns LocalDateTime.now()
            every { get("updated_at", LocalDateTime::class.java) } returns LocalDateTime.now()
        }

        val result = converter.convert(mockRow)

        assertEquals("test@example.com", result.email)
        assertEquals("John", result.firstname)
        assertEquals("Doe", result.lastname)
        assertEquals(SubscriberStatus.ENABLED, result.status)
    }
}
