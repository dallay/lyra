package com.lyra.app.newsletter.domain

import kotlinx.coroutines.flow.Flow

/**
 *
 * @created 6/1/24
 */
interface SubscriberRepository {
    suspend fun create(subscriber: Subscriber)
    suspend fun searchAll(): Flow<Subscriber>
    suspend fun searchActive(): Flow<Subscriber>
}
