package com.lyra.app.newsletter.tag.domain

/**
 * Repository interface for managing Tag entities.
 */
interface TagRepository {
    /**
     * Creates a new tag in the repository.
     *
     * @param tag The Tag entity to be created.
     */
    suspend fun create(tag: Tag)
}
