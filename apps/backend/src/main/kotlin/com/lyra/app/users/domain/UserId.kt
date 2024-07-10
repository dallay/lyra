package com.lyra.app.users.domain

import com.lyra.common.domain.BaseId
import java.util.*

/**
 * Represents a unique identifier for a user. It is a value object.
 *
 * @created 8/7/23
 * @param id The UUID value of the user ID.
 * @constructor Creates a new instance of UserId with the specified UUID value.
 * @see BaseId for more information about the base ID
 * @see UUID for more information about the UUID
 */
class UserId(private val id: UUID) : BaseId<UUID>(id) {
    /**
     * Constructs a new instance of the class with the specified ID.
     *
     * @param id The ID of the object as a string representation. It must be a valid UUID value.
     * (e.g. "123e4567-e89b-12d3-a456-426614174000") see [UUID] for more information about the UUID value.
     * @see UUID for more information about the UUID
     */
    constructor(id: String) : this(UUID.fromString(id))
    companion object {
        /**
         * Creates a new instance of the class with a random UUID value.
         *
         * @return A new instance of the class with a random UUID value.
         * @see UUID for more information about the UUID
         */
        fun create() = UserId(UUID.randomUUID())
    }
}
