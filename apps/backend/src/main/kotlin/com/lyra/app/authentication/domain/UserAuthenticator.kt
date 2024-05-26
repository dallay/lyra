package com.lyra.app.authentication.domain

import com.lyra.common.domain.vo.credential.Credential

/**
 * Represents a UserLogin that is responsible for logging in a user.
 * @created 31/7/23
 */
fun interface UserAuthenticator {
    /**
     * Login a user with the given username and password.
     *
     * @param username the username of the user to be logged in
     * @param password the password of the user to be logged in
     * @return the access token of the user
     */
    suspend fun authenticate(username: Username, password: Credential): AccessToken
}
