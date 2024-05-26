package com.lyra.app.authentication.infrastructure

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component

/**
 * Converter class that converts a Jwt into a collection of GrantedAuthority objects.
 * @since 1.0.0
 */
@Component
class JwtGrantedAuthorityConverter :
    Converter<Jwt, Collection<GrantedAuthority>> {
    /**
     * Convert the source object of type `S` to target type `T`.
     * @param jwt the source object to convert, which must be an instance of `S` (never `null`)
     * @return the converted object, which must be an instance of `T` (potentially `null`)
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    override fun convert(jwt: Jwt): Collection<GrantedAuthority> = Claims.extractAuthorityFromClaims(jwt.claims)
}
