package com.lyra.app.newsletter.subscriber.domain

import com.lyra.UnitTest
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@UnitTest
internal class SubscriberTest {
    private val email = "john.doe@lyra.com"
    private val firstname = "John"
    private val lastname = "Doe"

    @Test
    fun `should update name`() {
        val subscriber = Subscriber.create(
            UUID.randomUUID(),
            email,
            firstname,
            lastname,
            organizationId = UUID.randomUUID(),
        )
        val newName = Name("Jane", "Doe")
        subscriber.updateName(newName)
        assertEquals(newName, subscriber.name)
    }

    @Test
    fun `should update status`() {
        val subscriber = Subscriber.create(
            UUID.randomUUID(),
            email,
            firstname,
            lastname,
            organizationId = UUID.randomUUID(),
        )
        val newStatus = SubscriberStatus.BLOCKLISTED
        subscriber.updateStatus(newStatus)
        assertEquals(newStatus, subscriber.status)
    }

    @Test
    fun `should create a subscriber disabled`() {
        val subscriber = Subscriber.create(
            UUID.randomUUID(),
            email,
            firstname,
            lastname,
            SubscriberStatus.DISABLED,
            organizationId = UUID.randomUUID(),
        )
        assertEquals(SubscriberStatus.DISABLED, subscriber.status)
    }
}
