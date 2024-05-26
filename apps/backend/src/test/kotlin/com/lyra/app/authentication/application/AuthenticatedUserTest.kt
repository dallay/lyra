package com.lyra.app.authentication.application

import com.lyra.UnitTest
import com.lyra.app.authentication.domain.Role
import com.lyra.app.authentication.domain.Username
import com.lyra.app.authentication.domain.error.NotAuthenticatedUserException
import com.lyra.app.authentication.domain.error.UnknownAuthenticationException
import java.time.Instant
import java.util.stream.Stream
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.jose.jws.JwsAlgorithms
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken

@UnitTest
internal class AuthenticatedUserTest {
    @BeforeEach
    @AfterEach
    fun cleanup() {
        SecurityContextHolder.clearContext()
    }

    @Nested
    @DisplayName("Username")
    inner class AuthenticatedUserUsernameTest {
        @Test
        fun shouldNotGetNotAuthenticatedUserUsername() {
            assertThatThrownBy { AuthenticatedUser.username() }.isExactlyInstanceOf(
                NotAuthenticatedUserException::class.java,
            )
        }

        @Test
        fun shouldNotGetUsernameForUnknownAuthentication() {
            authenticate(TestingAuthenticationToken(null, null))
            assertThatThrownBy { AuthenticatedUser.username() }.isExactlyInstanceOf(
                UnknownAuthenticationException::class.java,
            )
        }

        @Test
        fun shouldNotGetUsernameForOAuthUserWithoutUsername() {
            authenticate(oAuth2AuthenticationTokenWithoutUsername())
            assertThatThrownBy { AuthenticatedUser.username() }.isExactlyInstanceOf(
                NotAuthenticatedUserException::class.java,
            )
        }

        @ParameterizedTest
        @MethodSource("com.lyra.app.authentication.application.AuthenticatedUserTest#allValidAuthentications")
        fun shouldGetAuthenticatedUserUsername(authentication: Authentication) {
            authenticate(authentication)
            assertThat(AuthenticatedUser.username()).isEqualTo(Username("admin"))
        }

        @Test
        fun shouldGetEmptyAuthenticatedUsernameForNotAuthenticatedUser() {
            assertThat(AuthenticatedUser.optionalUsername()).isEmpty()
        }

        @ParameterizedTest
        @MethodSource("com.lyra.app.authentication.application.AuthenticatedUserTest#allValidAuthentications")
        fun shouldGetOptionalAuthenticatedUserUsername(authentication: Authentication) {
            authenticate(authentication)
            assertThat(AuthenticatedUser.optionalUsername()).contains(Username("admin"))
        }

        @Test
        fun shouldNotGetOptionalUsernameForUnknownAuthentication() {
            authenticate(TestingAuthenticationToken(null, null))
            assertThatThrownBy { AuthenticatedUser.optionalUsername() }.isExactlyInstanceOf(
                UnknownAuthenticationException::class.java,
            )
        }
    }

    @Nested
    @DisplayName("Roles")
    internal inner class AuthenticatedUserRolesTest {
        @Test
        fun shouldGetEmptyRolesForNotAuthenticatedUser() {
            assertThat(AuthenticatedUser.roles().hasRole()).isFalse()
        }

        @Test
        fun shouldGetRolesFromClaim() {
            authenticate(oAuth2AuthenticationTokenWithUsername())
            assertThat(AuthenticatedUser.roles().roles).containsExactly(Role.USER)
        }
    }

    @Nested
    @DisplayName("Attributes")
    internal inner class AuthenticatedUserAttributesTest {
        @Test
        @DisplayName("should get attributes for OAuth2")
        fun shouldGetAttributesForOAuth2() {
            authenticate(oAuth2AuthenticationTokenWithUsername())
            assertThat(AuthenticatedUser.attributes()).containsEntry("preferred_username", "admin")
        }

        @Test
        @DisplayName("should get attributes for JWT")
        fun shouldGetAttributesForJWT() {
            authenticate(jwtAuthenticationToken())
            assertThat(AuthenticatedUser.attributes()).containsEntry("preferred_username", "admin")
        }

        @Test
        fun shouldNotGetAttributesForAnotherToken() {
            authenticate(usernamePasswordAuthenticationToken())
            assertThatThrownBy { AuthenticatedUser.attributes() }.isExactlyInstanceOf(
                UnknownAuthenticationException::class.java,
            )
        }

        @Test
        fun shouldNotGetAttributesForNotAuthenticatedUser() {
            assertThatThrownBy { AuthenticatedUser.attributes() }.isExactlyInstanceOf(
                NotAuthenticatedUserException::class.java,
            )
        }
    }

    private fun authenticate(token: Authentication) {
        val securityContext = SecurityContextHolder.createEmptyContext()
        securityContext.authentication = token
        SecurityContextHolder.setContext(securityContext)
    }

    companion object {
        private fun oAuth2AuthenticationTokenWithUsername(): OAuth2AuthenticationToken {
            return oAuth2AuthenticationToken(
                mapOf(
                    "groups" to Role.USER.key(),
                    "sub" to 123,
                    "preferred_username" to "admin",
                ),
            )
        }

        private fun oAuth2AuthenticationTokenWithoutUsername(): OAuth2AuthenticationToken {
            return oAuth2AuthenticationToken(
                mapOf(
                    "groups" to Role.USER.key(),
                    "sub" to 123,
                ),
            )
        }

        private fun oAuth2AuthenticationToken(claims: Map<String, Any>): OAuth2AuthenticationToken {
            val idToken = OidcIdToken(OidcParameterNames.ID_TOKEN, Instant.now(), Instant.now().plusSeconds(60), claims)
            val authorities: Collection<GrantedAuthority> = listOf<GrantedAuthority>(
                SimpleGrantedAuthority(
                    Role.USER.key(),
                ),
            )
            val user: OidcUser = DefaultOidcUser(authorities, idToken)
            return OAuth2AuthenticationToken(user, authorities, "oidc")
        }

        private fun jwtAuthenticationToken(): JwtAuthenticationToken {
            val jwt = Jwt
                .withTokenValue("token")
                .header("alg", JwsAlgorithms.RS256)
                .subject("jhipster")
                .claim("preferred_username", "admin")
                .build()
            return JwtAuthenticationToken(jwt, adminAuthorities())
        }

        private fun usernamePasswordAuthenticationToken(): UsernamePasswordAuthenticationToken {
            val authorities: Collection<GrantedAuthority> =
                adminAuthorities()
            val user =
                User("admin", "admin", authorities)
            return UsernamePasswordAuthenticationToken(user, "admin", authorities)
        }

        private fun adminAuthorities(): List<GrantedAuthority> =
            listOf<GrantedAuthority>(SimpleGrantedAuthority(Role.ADMIN.key()))

        @Suppress("unused")
        @JvmStatic
        private fun allValidAuthentications(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(usernamePasswordAuthenticationToken()),
                Arguments.of(oAuth2AuthenticationTokenWithUsername()),
                Arguments.of(jwtAuthenticationToken()),
                Arguments.of(UsernamePasswordAuthenticationToken("admin", "admin")),
            )
        }
    }
}
