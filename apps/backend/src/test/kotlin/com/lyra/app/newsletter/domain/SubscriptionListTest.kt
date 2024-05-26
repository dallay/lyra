package com.lyra.app.newsletter.domain

import com.lyra.UnitTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@UnitTest
internal class SubscriptionListTest {
    private val name = "My list"
    private val email = "john.doe@lyra.com"
    private val firstname = "John"
    private val lastname = "Doe"

    @Test
    fun `should update name`() {
        val subscriptionList = SubscriptionList.create(name)
        val newName = "My new list"
        subscriptionList.updateName(newName)
        assertEquals(newName, subscriptionList.name)
    }

    @Test
    fun `should update description`() {
        val subscriptionList = SubscriptionList.create(name)
        val newDescription = "My new description"
        subscriptionList.updateDescription(newDescription)
        assertEquals(newDescription, subscriptionList.description)
    }

    @Test
    fun `should add subscriber`() {
        val subscriptionList = SubscriptionList.create(name)
        val subscriber = Subscriber.create(email, firstname, lastname)
        subscriptionList.addSubscriber(subscriber)
        assertEquals(1, subscriptionList.subscribers.size)
    }

    @Test
    fun `should remove subscriber`() {
        val subscriptionList = SubscriptionList.create(name)
        val subscriber = Subscriber.create(email, firstname, lastname)
        subscriptionList.addSubscriber(subscriber)
        subscriptionList.removeSubscriber(subscriber)
        assertEquals(0, subscriptionList.subscribers.size)
    }

    @Test
    fun `should update subscriber`() {
        val subscriptionList = SubscriptionList.create(name)
        val subscriber = Subscriber.create(email, firstname, lastname)
        subscriptionList.addSubscriber(subscriber)
        val newName = Name("Jane", "Doe")
        subscriber.updateName(newName)
        assertEquals(newName, subscriptionList.subscribers[0].name)
    }
}
