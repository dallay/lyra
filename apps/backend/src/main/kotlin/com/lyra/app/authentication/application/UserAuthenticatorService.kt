package com.lyra.app.authentication.application

import com.lyra.app.authentication.domain.AccessToken
import com.lyra.app.authentication.domain.UserAuthenticator
import com.lyra.app.authentication.domain.Username
import com.lyra.common.domain.Service
import com.lyra.common.domain.vo.credential.Credential
import org.slf4j.LoggerFactory

/**
 *
 * @created 31/7/23
 */
@Service
class UserAuthenticatorService(private val userAuthenticator: UserAuthenticator) {
    /**
     * Authenticates a user.
     *
     * @param username the username of the user to be authenticated
     * @param password the password of the user to be authenticated
     * @return the access token of the user
     */
    suspend fun authenticate(username: Username, password: Credential): AccessToken {
        log.info("Authenticating user with username: {}", username)
        return userAuthenticator.authenticate(username, password)
    }

    companion object {
        private val log = LoggerFactory.getLogger(UserAuthenticatorService::class.java)
    }
}
