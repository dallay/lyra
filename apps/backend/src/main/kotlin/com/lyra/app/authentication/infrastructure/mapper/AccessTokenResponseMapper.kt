package com.lyra.app.authentication.infrastructure.mapper

import com.lyra.app.authentication.domain.AccessToken
import org.keycloak.representations.AccessTokenResponse

/**
 * Class for mapping an [AccessTokenResponse] object to an [AccessToken] object.
 */
object AccessTokenResponseMapper {
    /**
     * Converts an [AccessTokenResponse] object to an AccessToken object.
     *
     * @return The converted [AccessToken] object.
     */
    fun AccessTokenResponse.toAccessToken(): AccessToken = AccessToken(
        token = this.token,
        expiresIn = this.expiresIn,
        refreshToken = this.refreshToken,
        refreshExpiresIn = this.refreshExpiresIn,
        tokenType = this.tokenType,
        notBeforePolicy = this.notBeforePolicy,
        sessionState = this.sessionState,
        scope = this.scope,
    )
}
