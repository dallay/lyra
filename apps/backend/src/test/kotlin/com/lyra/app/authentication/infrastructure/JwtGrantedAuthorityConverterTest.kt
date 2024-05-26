package com.lyra.app.authentication.infrastructure

import com.lyra.UnitTest
import com.lyra.app.authentication.domain.Role
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jose.jws.JwsAlgorithms
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.test.context.junit.jupiter.SpringExtension

private const val SUBJECT = "lyra"

@UnitTest
@ExtendWith(SpringExtension::class)
internal class JwtGrantedAuthorityConverterTest {
    @InjectMocks
    lateinit var jwtGrantedAuthorityConverter: JwtGrantedAuthorityConverter

    @Test
    fun shouldConvert() {
        val jwt = Jwt
            .withTokenValue("token")
            .header("alg", JwsAlgorithms.RS256)
            .subject(SUBJECT)
            .claim("roles", listOf("ROLE_ADMIN"))
            .build()
        val result = jwtGrantedAuthorityConverter.convert(jwt)
        assertThat(result).containsExactly(SimpleGrantedAuthority(Role.ADMIN.key()))
    }

    @Test
    fun shouldConvertButEmpty() {
        val jwt = Jwt.withTokenValue("token").header("alg", JwsAlgorithms.RS256).subject(SUBJECT).build()
        val result = jwtGrantedAuthorityConverter.convert(jwt)
        assertThat(result).isEmpty()
    }
}
