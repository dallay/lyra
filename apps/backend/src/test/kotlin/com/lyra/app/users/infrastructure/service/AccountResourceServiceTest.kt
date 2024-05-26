package com.lyra.app.users.infrastructure.service

import com.lyra.UnitTest
import com.lyra.app.authentication.domain.AuthoritiesConstants
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken

@UnitTest
class AccountResourceServiceTest {

    private lateinit var accountResourceService: AccountResourceService
    private val userDetails: MutableMap<String, Any> = mutableMapOf(
        "sub" to "test",
        "preferred_username" to "test",
        "email" to "test@localhost",
        "given_name" to "Test",
        "family_name" to "User",
        "roles" to listOf(AuthoritiesConstants.USER),
    )

    @BeforeEach
    fun setUp() {
        accountResourceService = AccountResourceService()
    }

    @Test
    fun `should get account information successfully by OAuth2AuthenticationToken`() {
        val authenticationToken = createMockOAuth2AuthenticationToken(userDetails)
        val userResponse = accountResourceService.getAccount(authenticationToken).block()
        assertEquals("test", userResponse?.username)
        assertEquals("test@localhost", userResponse?.email)
        assertEquals("Test", userResponse?.firstname)
        assertEquals("User", userResponse?.lastname)
        assertEquals(1, userResponse?.authorities?.size)
        assertEquals(AuthoritiesConstants.USER, userResponse?.authorities?.first())
    }

    @Test
    fun `should get account information successfully with multiple roles by OAuth2AuthenticationToken`() {
        userDetails["roles"] = listOf(AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN)
        val authenticationToken = createMockOAuth2AuthenticationToken(userDetails)
        val userResponse = accountResourceService.getAccount(authenticationToken).block()
        assertEquals("test", userResponse?.username)
        assertEquals("test@localhost", userResponse?.email)
        assertEquals("Test", userResponse?.firstname)
        assertEquals("User", userResponse?.lastname)
        assertEquals(2, userResponse?.authorities?.size)
        assertTrue(userResponse?.authorities?.contains(AuthoritiesConstants.USER) ?: false)
        assertTrue(userResponse?.authorities?.contains(AuthoritiesConstants.ADMIN) ?: false)
    }

    @Test
    fun `should get account information successfully by JwtAuthenticationToken`() {
        val authenticationToken = createMockJwtAuthenticationToken(userDetails)
        val userResponse = accountResourceService.getAccount(authenticationToken).block()
        assertEquals("test", userResponse?.username)
        assertEquals("test@localhost", userResponse?.email)
        assertEquals("Test", userResponse?.firstname)
        assertEquals("User", userResponse?.lastname)
        assertEquals(1, userResponse?.authorities?.size)
        assertEquals(AuthoritiesConstants.USER, userResponse?.authorities?.first())
    }

    @Test
    fun `should get account information successfully with multiple roles by JwtAuthenticationToken`() {
        userDetails["roles"] = listOf(AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN)
        val authenticationToken = createMockJwtAuthenticationToken(userDetails)
        val userResponse = accountResourceService.getAccount(authenticationToken).block()
        assertEquals("test", userResponse?.username)
        assertEquals("test@localhost", userResponse?.email)
        assertEquals("Test", userResponse?.firstname)
        assertEquals("User", userResponse?.lastname)
        assertEquals(2, userResponse?.authorities?.size)
        assertTrue(userResponse?.authorities?.contains(AuthoritiesConstants.USER) ?: false)
        assertTrue(userResponse?.authorities?.contains(AuthoritiesConstants.ADMIN) ?: false)
    }

    @Test
    fun `should throw IllegalArgumentException when authentication token is not OAuth2 or JWT`() {
        val authenticationToken = UsernamePasswordAuthenticationToken("test", "test")
        assertThrows<IllegalArgumentException> {
            accountResourceService.getAccount(authenticationToken).block()
        }
    }

    private fun createMockOAuth2AuthenticationToken(userDetails: Map<String, Any>): OAuth2AuthenticationToken {
        val authorities: Collection<GrantedAuthority> =
            listOf<GrantedAuthority>(SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS))
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
            "anonymous",
            "anonymous",
            authorities,
        )
        usernamePasswordAuthenticationToken.details = userDetails
        val user: OAuth2User = DefaultOAuth2User(authorities, userDetails, "sub")
        return OAuth2AuthenticationToken(user, authorities, "oidc")
    }

    private fun createMockJwtAuthenticationToken(userDetails: Map<String, Any>): JwtAuthenticationToken {
        val jwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .claim("sub", userDetails["sub"])
            .claim("preferred_username", userDetails["preferred_username"])
            .claim("email", userDetails["email"])
            .claim("given_name", userDetails["given_name"])
            .claim("family_name", userDetails["family_name"])
            .claim("roles", userDetails["roles"])
            .build()
        val authorities: Collection<GrantedAuthority> = AuthorityUtils.createAuthorityList(
            AuthoritiesConstants.ANONYMOUS,
        )
        return JwtAuthenticationToken(jwt, authorities)
    }
}
