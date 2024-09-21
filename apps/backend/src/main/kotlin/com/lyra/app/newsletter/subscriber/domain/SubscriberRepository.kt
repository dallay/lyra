package com.lyra.app.newsletter.subscriber.domain

/**
 * This is an interface for a repository that handles subscriber data.
 * It has five functions, create, searchAllByOffset, searchAllByCursor,
 * and searchActive, which are all suspending functions.
 */
interface SubscriberRepository {
    /**
     * This function is used to create a new subscriber.
     * It is a suspending function, meaning it can be paused and resumed at a later time.
     * This makes it suitable for use in a coroutine context, where it can be used for non-blocking IO operations.
     *
     * @param subscriber The subscriber to create.
     */
    suspend fun create(subscriber: Subscriber)
}
