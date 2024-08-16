package com.lyra.app.authentication.domain

/**
 * A functional interface representing a user authenticator logout operation.
 * This interface defines a single abstract method `logout` that must be implemented.
 */
fun interface UserAuthenticatorLogout {
    /**
     * Logs out the user.
     * This method is a suspend function, meaning it can be called from a coroutine or another suspend function.
     * @param refreshToken The refresh token used to authenticate the user.
     */
    suspend fun logout(refreshToken: String)
}
