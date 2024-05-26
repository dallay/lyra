package com.lyra.app.authentication.domain

import com.lyra.common.domain.bus.query.Response

/**
 * Represents an access token returned by the server.
 *
 * @property token The access token value.
 * @property expiresIn The time in seconds until the access token expires.
 * @property refreshToken The refresh token value.
 * @property refreshExpiresIn The time in seconds until the refresh token expires.
 * @property tokenType The type of the token.
 * @property notBeforePolicy The time in seconds from the token's creation,
 *           before which it should not be considered valid.
 * @property sessionState The session state associated with the access token.
 * @property scope The scope of the access token.
 *
 * @constructor Creates a new instance of AccessToken.
 * @since 1.0
 * @created 31/7/23
 */
data class AccessToken(
    val token: String,
    val expiresIn: Long,
    val refreshToken: String,
    val refreshExpiresIn: Long,
    val tokenType: String,
    val notBeforePolicy: Int? = null,
    val sessionState: String? = null,
    val scope: String? = null
) : Response
