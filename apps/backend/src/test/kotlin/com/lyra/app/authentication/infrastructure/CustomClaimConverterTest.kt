package com.lyra.app.authentication.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.lyra.UnitTest
import com.lyra.app.authentication.domain.Role
import java.net.SocketTimeoutException
import java.util.Map.entry
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

private const val CLAIM_URL = "https://lyra.com"

@UnitTest
@ExtendWith(MockitoExtension::class)
internal class CustomClaimConverterTest {
    @Mock
    private lateinit var webClient: WebClient
    private lateinit var converter: CustomClaimConverter
    private val token: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
        ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsIm" +
        "p0aSI6ImQzNWRmMTRkLTA5ZjYtNDhmZi04YTkzLTdjNmYwMzM5MzE1OSIsImlhdCI6MTU0M" +
        "Tk3MTU4MywiZXhwIjoxNTQxOTc1MTgzfQ.QaQOarmV8xEUYV7yvWzX3cUE_4W1luMcWCwpr" +
        "oqqUrg"

    @BeforeEach
    fun loadConverter() {
        converter = CustomClaimConverter(buildRegistration(), webClient, token)
    }

    private fun buildRegistration(): ClientRegistration {
        return ClientRegistration
            .withRegistrationId("test")
            .userInfoUri(CLAIM_URL)
            .authorizationGrantType(AuthorizationGrantType.JWT_BEARER)
            .build()
    }

    @Test
    fun shouldNotGetClaimFromInputClaimWithTimeoutRequest() {
        mockRestTimeout()
        val convertedClaims = converter.convert(simpleClaim())
        assertThat(convertedClaims).containsExactly(entry("sub", "123"))
    }

    @Test
    fun shouldConvertFullClaim() {
        user()
            .username("bob")
            .givenName("John")
            .familyName("Doe")
            .email("john.doe@company.com")
            .groups(Role.ADMIN.key(), Role.USER.key())
            .namespaceRoles(Role.ADMIN.key(), Role.USER.key())
            .mock()

        val convertedClaims = converter.convert(simpleClaim())
        assertThat(convertedClaims)
            .containsEntry("sub", "123")
            .containsEntry("preferred_username", "bob")
            .containsEntry("given_name", "John")
            .containsEntry("family_name", "Doe")
            .containsEntry("email", "john.doe@company.com")
            .containsEntry("groups", listOf(Role.ADMIN.key(), Role.USER.key()))
            .containsEntry("roles", listOf(Role.ADMIN.key(), Role.USER.key()))
    }

    @Test
    fun shouldConvertClaimForUnknownUser() {
        mockUser(null)

        val convertedClaims = converter.convert(simpleClaim())
        assertThat(convertedClaims).containsExactly(entry("sub", "123"))
    }

    @Test
    fun shouldConvertClaimForEmptyUser() {
        user().mock()

        val convertedClaims = converter.convert(simpleClaim())
        assertThat(convertedClaims).containsExactly(entry("sub", "123"))
    }

    @Test
    fun shouldIgnoreRolesFromAnotherNamespace() {
        user().roles(Role.ADMIN.key(), Role.USER.key()).mock()

        val convertedClaims = converter.convert(simpleClaim())
        assertThat(convertedClaims).doesNotContainKey("roles")
    }

    @Test
    fun shouldConvertClaimWithName() {
        user().username("bob").name("John Doe").mock()

        val convertedClaims = converter.convert(simpleClaim())
        assertThat(convertedClaims)
            .containsEntry("sub", "123")
            .containsEntry("preferred_username", "bob")
            .containsEntry("given_name", "John")
            .containsEntry("family_name", "Doe")
    }

    @Test
    fun shouldConvertClaimWithBlank() {
        user().username("bob").name(" ").mock()

        val convertedClaims = converter.convert(simpleClaim())
        assertThat(convertedClaims)
            .containsEntry("sub", "123")
            .containsEntry("preferred_username", "bob")
            .doesNotContainKey("given_name")
            .doesNotContainKey("family_name")
    }

    @Test
    fun shouldConvertClaimWithComposedName() {
        user().username("bob").name("John Doe Sr.").mock()

        val convertedClaims = converter.convert(simpleClaim())
        assertThat(convertedClaims)
            .containsEntry("sub", "123")
            .containsEntry("preferred_username", "bob")
            .containsEntry("given_name", "John")
            .containsEntry("family_name", "Doe Sr.")
    }

    @Test
    fun shouldGetUserFromMemoryCache() {
        user().username("bob").name("John Doe Sr.").mock()

        converter.convert(simpleClaim())
        val convertedClaims = converter.convert(simpleClaim())
        assertThat(convertedClaims)
            .containsEntry("sub", "123")
            .containsEntry("preferred_username", "bob")
            .containsEntry("given_name", "John")
            .containsEntry("family_name", "Doe Sr.")
    }

    @Test
    fun shouldGetFirstUserFromMemoryCache() {
        user().username("bob").name("John Doe Sr.").mock()
        converter.convert(simpleClaim())
        converter.convert(anotherSimpleClaim())
        val convertedClaims = converter.convert(simpleClaim())
        assertThat(convertedClaims)
            .containsEntry("sub", "123")
            .containsEntry("preferred_username", "bob")
            .containsEntry("given_name", "John")
            .containsEntry("family_name", "Doe Sr.")
    }

    private fun user(): UserBuilder = UserBuilder(this::mockUser)

    private class UserBuilder(private val mockUser: (ObjectNode) -> Unit) {
        private val user: ObjectNode = json.createObjectNode()

        fun username(username: String?): UserBuilder {
            user.put("preferred_username", username)
            return this
        }

        fun givenName(givenName: String?): UserBuilder {
            user.put("given_name", givenName)
            return this
        }

        fun familyName(familyName: String?): UserBuilder {
            user.put("family_name", familyName)
            return this
        }

        fun email(email: String?): UserBuilder {
            user.put("email", email)
            return this
        }

        fun groups(vararg groups: String): UserBuilder {
            val userGroups: ArrayNode = user.putArray("groups")
            listOf(*groups).forEach(userGroups::add)
            return this
        }

        fun namespaceRoles(vararg roles: String): UserBuilder {
            val userRoles = user.putArray(Claims.CLAIMS_NAMESPACE + "roles")
            listOf(*roles).forEach(userRoles::add)
            return this
        }

        fun roles(vararg roles: String): UserBuilder {
            val userRoles = user.putArray("dummyroles")
            listOf(*roles).forEach(userRoles::add)
            return this
        }

        fun name(name: String): UserBuilder {
            user.put("name", name)
            return this
        }

        fun mock() {
            mockUser(user)
        }

        companion object {
            private val json = ObjectMapper()
        }
    }

    private fun mockUser(user: ObjectNode?) {
        val requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec::class.java)
        val responseSpec = mock(WebClient.ResponseSpec::class.java)

        `when`(webClient.get()).thenReturn(requestHeadersUriSpec)
        `when`(requestHeadersUriSpec.uri(eq(CLAIM_URL))).thenReturn(requestHeadersUriSpec)
        `when`(requestHeadersUriSpec.headers(any())).thenReturn(requestHeadersUriSpec)
        `when`(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec)
        `when`(responseSpec.bodyToMono(ObjectNode::class.java)).thenReturn(Mono.justOrEmpty(user))
    }

    private fun mockRestTimeout() {
        val requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec::class.java)
        val responseSpec = mock(WebClient.ResponseSpec::class.java)

        `when`(webClient.get()).thenReturn(requestHeadersUriSpec)
        `when`(requestHeadersUriSpec.uri(eq(CLAIM_URL))).thenReturn(requestHeadersUriSpec)
        `when`(requestHeadersUriSpec.headers(any())).thenReturn(requestHeadersUriSpec)
        `when`(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec)
        `when`(responseSpec.bodyToMono(ObjectNode::class.java)).thenThrow(
            ResourceAccessException(
                "timeout",
                SocketTimeoutException(),
            ),
        )
    }
    companion object {
        private fun simpleClaim(): Map<String, Any> = mapOf("sub" to "123")

        private fun anotherSimpleClaim(): Map<String, Any> = mapOf("sub" to "456")
    }
}
