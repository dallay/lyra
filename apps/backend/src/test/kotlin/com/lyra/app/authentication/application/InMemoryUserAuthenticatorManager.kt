package com.lyra.app.authentication.application

import com.lyra.app.authentication.domain.AccessToken
import com.lyra.app.authentication.domain.RefreshToken
import com.lyra.app.authentication.domain.RefreshTokenManager
import com.lyra.app.authentication.domain.UserAuthenticationException
import com.lyra.app.authentication.domain.UserAuthenticator
import com.lyra.app.authentication.domain.Username
import com.lyra.common.domain.vo.credential.Credential

/**
 * InMemoryUserAuthenticator is a class that implements the [UserAuthenticator] and [RefreshTokenManager] interfaces.
 * It provides the functionality to authenticate a user with a given username and password.
 * It also provides the functionality to refresh the access token of a user with a given refresh token.
 * @created 2/8/23
 */
class InMemoryUserAuthenticatorManager(
    private val database: MutableMap<String, String> = mutableMapOf()
) : UserAuthenticator, RefreshTokenManager {
    /**
     * Login a user with the given username and password.
     *
     * @param username the username of the user to be logged in
     * @param password the password of the user to be logged in
     * @return the access token of the user
     */
    override suspend fun authenticate(username: Username, password: Credential): AccessToken {
        return if (database[username.value] == password.value) {
            accessToken()
        } else {
            throw UserAuthenticationException("Invalid account. User probably hasn't verified email.")
        }
    }

    /**
     * Adds a user to the database.
     *
     * @param username the username of the user to be added
     * @param password the password of the user to be added
     */
    fun addUser(username: String, password: String) = database.put(username, password)

    /**
     * Clears the database.
     */
    fun clear() = database.clear()

    /**
     * Refreshes the access token of the user.
     *
     * @param refreshToken the refresh token of the user
     * @return the access token of the user
     */
    override suspend fun refresh(refreshToken: RefreshToken): AccessToken = accessToken()

    private fun accessToken() = AccessToken(
        "token",
        1,
        "refreshToken",
        1,
        "type",
        1,
        "81945a53-7bfa-4347-9321-b46c2a2a736d",
        "email profile",
    )
}
