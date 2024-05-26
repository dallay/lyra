package com.lyra.app.users.domain

/**
 * Represents a UserCreator that is responsible for creating a user.
 *
 * A UserCreator is a functional interface with a single method `create`, which takes a `User` object
 * and asynchronously creates a user.
 * @created 8/7/23
 */
fun interface UserCreator {
    /**
     * Create a new user.
     *
     * @param user The user object to be created.
     * @return The created user.
     */
    suspend fun create(user: User): User
}
