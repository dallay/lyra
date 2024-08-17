package com.lyra.app.authentication.application.logout

import com.lyra.app.authentication.domain.UserAuthenticatorLogout
import com.lyra.common.domain.Service

/**
 * This service class is responsible for logging out a user.
 * It uses an instance of [UserAuthenticatorLogout] to perform the logout operation.
 *
 * @property userAuthenticatorLogout An instance of [UserAuthenticatorLogout] used to log out the user.
 */
@Service
class UserLogoutService(private val userAuthenticatorLogout: UserAuthenticatorLogout) {
    /**
     * Logs out the user.
     * This method is a suspend function, meaning it can be called from a coroutine or another suspend function.
     * @param refreshToken The refresh token used to authenticate the user.
     */
    suspend fun logout(refreshToken: String) {
        log.debug("Logging out user")
        userAuthenticatorLogout.logout(refreshToken)
    }
    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(UserLogoutService::class.java)
    }
}
