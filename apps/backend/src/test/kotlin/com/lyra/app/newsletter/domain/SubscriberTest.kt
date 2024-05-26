package com.lyra.app.newsletter.domain

import com.lyra.UnitTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@UnitTest
internal class SubscriberTest {
    private val email = "john.doe@lyra.com"
    private val firstname = "John"
    private val lastname = "Doe"

    @Test
    fun `should update name`() {
        val subscriber = Subscriber.create(email, firstname, lastname)
        val newName = Name("Jane", "Doe")
        subscriber.updateName(newName)
        assertEquals(newName, subscriber.name)
    }

    @Test
    fun `should update status`() {
        val subscriber = Subscriber.create(email, firstname, lastname)
        val newStatus = SubscriberStatus.BLOCKLISTED
        subscriber.updateStatus(newStatus)
        assertEquals(newStatus, subscriber.status)
    }

    @Test
    fun `should create a subscriber disabled`() {
        val subscriber = Subscriber.create(email, firstname, lastname, SubscriberStatus.DISABLED)
        assertEquals(SubscriberStatus.DISABLED, subscriber.status)
    }
}
