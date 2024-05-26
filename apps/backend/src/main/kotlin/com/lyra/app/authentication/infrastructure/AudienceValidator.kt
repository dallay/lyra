package com.lyra.app.authentication.infrastructure

import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.util.Assert

/**
 * A validator that validates the audience of an OAuth2 JWT token.
 *
 * @param allowedAudience The list of allowed audience values.
 * @constructor Creates a new instance of [AudienceValidator].
 * @see <a href="https://tools.ietf.org/html/rfc7519#section-4.1.3">(Audience) Claim</a>
 * @see <a href="https://openid.net/specs/openid-connect-core-1_0.html#IDToken">openid-connect</a>
 */
internal class AudienceValidator(allowedAudience: List<String>) : OAuth2TokenValidator<Jwt> {
    private val log = LoggerFactory.getLogger(AudienceValidator::class.java)
    private val error = OAuth2Error("invalid_token", "The required audience is missing", null)
    private val allowedAudience: List<String>

    init {
        Assert.notEmpty(allowedAudience, "Allowed audience should not be null or empty.")
        this.allowedAudience = allowedAudience
    }

    /**
     * Validates the provided JWT.
     *
     * @param jwt The [Jwt] to be validated.
     * @return An [OAuth2TokenValidatorResult] representing the validation result.
     */
    override fun validate(jwt: Jwt): OAuth2TokenValidatorResult {
        val audience = jwt.audience
        if (audience.stream().anyMatch(allowedAudience::contains)) {
            return OAuth2TokenValidatorResult.success()
        }
        log.warn("Invalid audience: {}", audience)
        return OAuth2TokenValidatorResult.failure(error)
    }
}
