package com.lyra.app.users.application

import com.lyra.app.users.domain.User
import com.lyra.app.users.domain.UserCreator
import com.lyra.app.users.domain.UserStoreException
import java.util.concurrent.ConcurrentHashMap

class InMemoryUserRepository(
    private val users: MutableMap<String, User> = ConcurrentHashMap()
) : UserCreator {

    /**
     * Create a new user.
     *
     * @param user The user object to be created.
     * @return The created User object.
     * @throws UserStoreException if a user with the same email or username already exists.
     */
    override suspend fun create(user: User): User {
        if (checkIfUserExist(user)) {
            throw UserStoreException(
                "User with email: ${user.email.value} or username: ${user.username.value} already exists.",
            )
        }
        users[user.id.value.toString()] = user
        return user
    }

    private fun checkIfUserExist(user: User): Boolean {
        return getUser(user.id.value.toString()) != null ||
            getUserByEmail(user.email.value) != null ||
            getUserByUsername(user.username.value) != null
    }

    private fun getUser(userId: String): User? = users[userId]

    private fun getUserByEmail(email: String): User? = users.values.firstOrNull { it.email.value == email }

    private fun getUserByUsername(username: String): User? = users.values.firstOrNull { it.username.value == username }
}
