package com.lyra.app.authentication.infrastructure

import com.lyra.UnitTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.security.oauth2.jwt.Jwt

/**
 * Test class for the [AudienceValidator] utility class.
 */
@UnitTest
internal class AudienceValidatorTest {
    private val validator = AudienceValidator(listOf("api://default"))

    @Test
    fun shouldInvalidAudience() {
        val badJwt: Jwt = mock(Jwt::class.java)
        `when`(badJwt.audience).thenReturn(listOf("bar"))
        assertThat(validator.validate(badJwt).hasErrors()).isTrue()
    }

    @Test
    fun shouldValidAudience() {
        val jwt: Jwt = mock(Jwt::class.java)
        `when`(jwt.audience).thenReturn(listOf("api://default"))
        assertThat(validator.validate(jwt).hasErrors()).isFalse()
    }
}
