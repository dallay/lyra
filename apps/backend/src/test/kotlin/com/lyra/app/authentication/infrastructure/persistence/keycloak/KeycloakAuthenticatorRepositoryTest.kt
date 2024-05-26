package com.lyra.app.authentication.infrastructure.persistence.keycloak

import com.lyra.UnitTest
import com.lyra.app.CredentialGenerator
import com.lyra.app.authentication.domain.AccessToken
import com.lyra.app.authentication.domain.UserAuthenticationException
import com.lyra.app.authentication.domain.Username
import com.lyra.app.authentication.infrastructure.ApplicationSecurityProperties
import com.lyra.common.domain.vo.credential.Credential
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import net.datafaker.Faker
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.admin.client.token.TokenManager
import org.keycloak.representations.AccessTokenResponse

private const val REALM = "realm"

private const val SERVER_URL = "http://localhost/auth"

private const val CLIENT_ID = "client"

private const val CLIENT_SECRET = "secret"

@UnitTest
class KeycloakAuthenticatorRepositoryTest {
    private val faker = Faker()
    private val applicationSecurityProperties = mockk<ApplicationSecurityProperties>()
    private val keycloakAuthenticatorRepository = KeycloakAuthenticatorRepository(applicationSecurityProperties)
    private val keycloak = mockk<Keycloak>()
    private val keycloakBuilder = mockk<KeycloakBuilder>()
    private val tokenManager = mockk<TokenManager>()
    private val expectedAccessToken =
        AccessToken("access_token", 3600L, "refresh_token", 3600L, "bearer", 0, "session", "scope")

    @BeforeEach
    fun setUp() {
        mockkStatic(KeycloakBuilder::class)
        mockkStatic(TokenManager::class)

        val accessTokenResponse = mockk<AccessTokenResponse> {
            every { token } returns expectedAccessToken.token
            every { expiresIn } returns expectedAccessToken.expiresIn
            every { refreshToken } returns expectedAccessToken.refreshToken
            every { refreshExpiresIn } returns expectedAccessToken.refreshExpiresIn
            every { tokenType } returns expectedAccessToken.tokenType
            every { notBeforePolicy } returns expectedAccessToken.notBeforePolicy!!
            every { sessionState } returns expectedAccessToken.sessionState
            every { scope } returns expectedAccessToken.scope
        }

        every { applicationSecurityProperties.oauth2.realm } returns REALM
        every { applicationSecurityProperties.oauth2.serverUrl } returns SERVER_URL
        every { applicationSecurityProperties.oauth2.clientId } returns CLIENT_ID
        every { applicationSecurityProperties.oauth2.clientSecret } returns CLIENT_SECRET

        every {
            KeycloakBuilder.builder()
        } returns keycloakBuilder

        every {
            keycloakBuilder.realm(any())
        } returns keycloakBuilder

        every {
            keycloakBuilder.serverUrl(any())
        } returns keycloakBuilder

        every {
            keycloakBuilder.clientId(any())
        } returns keycloakBuilder

        every {
            keycloakBuilder.clientSecret(any())
        } returns keycloakBuilder

        every {
            keycloakBuilder.grantType(any())
        } returns keycloakBuilder

        every {
            keycloakBuilder.username(any())
        } returns keycloakBuilder

        every {
            keycloakBuilder.password(any())
        } returns keycloakBuilder

        every {
            keycloakBuilder.build()
        } returns keycloak

        every {
            keycloak.tokenManager()
        } returns tokenManager

        coEvery {
            tokenManager.getAccessToken()
        } returns accessTokenResponse
    }

    @Test
    fun `should authenticate user and return access token on success`(): Unit = runBlocking {
        // Arrange
        val username = Username(faker.internet().emailAddress())
        val password = CredentialGenerator.generate(faker.internet().password(8, 80, true, true, true))

        val usernameSlot = slot<String>()
        val passwordSlot = slot<String>()
        // Act
        val result = keycloakAuthenticatorRepository.authenticate(username, password)

        // Assert
        assertEquals(expectedAccessToken, result)

        // Verify
        verifyAuthenticationCalls(usernameSlot, passwordSlot, username, password)
    }

    @Test
    fun `should throw UserAuthenticationException when authentication fails`(): Unit = runBlocking {
        // Arrange
        val username = Username(faker.internet().emailAddress())
        val password = CredentialGenerator.generate(faker.internet().password(8, 80, true, true, true))

        val usernameSlot = slot<String>()
        val passwordSlot = slot<String>()
        val exception = mockk<UserAuthenticationException>()

        coEvery {
            tokenManager.getAccessToken()
        } throws exception

        // Act
        val result = runCatching {
            keycloakAuthenticatorRepository.authenticate(username, password)
        }

        // Assert
        assertEquals(exception, result.exceptionOrNull())

        // Verify
        verifyAuthenticationCalls(usernameSlot, passwordSlot, username, password)
    }

    private fun verifyAuthenticationCalls(
        usernameSlot: CapturingSlot<String>,
        passwordSlot: CapturingSlot<String>,
        username: Username,
        password: Credential
    ) {
        coVerify(exactly = 1) {
            keycloakBuilder.realm(REALM)
            keycloakBuilder.serverUrl(SERVER_URL)
            keycloakBuilder.clientId(CLIENT_ID)
            keycloakBuilder.clientSecret(CLIENT_SECRET)
            keycloakBuilder.username(capture(usernameSlot))
            keycloakBuilder.password(capture(passwordSlot))
            keycloakBuilder.build()
            keycloak.tokenManager()
            tokenManager.getAccessToken()
        }
        assertEquals(username.value, usernameSlot.captured)
        assertEquals(password.value, passwordSlot.captured)
    }
}
