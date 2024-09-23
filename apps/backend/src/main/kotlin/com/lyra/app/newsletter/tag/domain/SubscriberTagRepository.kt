package com.lyra.app.newsletter.tag.domain

/**
 * Repository interface for managing SubscriberTag entities.
 */
interface SubscriberTagRepository {
    /**
     * Creates a new tag in the repository.
     *
     * @param tag The SubscriberTag entity to be created.
     */
    suspend fun create(tag: SubscriberTag)

    /**
     * Deletes a tag from the repository.
     *
     * @param tag The SubscriberTag entity to be deleted.
     */
    suspend fun delete(tag: SubscriberTag)
}
