package com.lyra.app.authentication.infrastructure.persistence.keycloak

import com.lyra.UnitTest
import com.lyra.app.authentication.domain.AccessToken
import com.lyra.app.authentication.domain.RefreshToken
import com.lyra.app.authentication.infrastructure.ApplicationSecurityProperties
import io.mockk.every
import io.mockk.mockk
import java.net.URI
import kotlinx.coroutines.runBlocking
import net.datafaker.Faker
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.keycloak.representations.AccessTokenResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono

private const val REALM = "realm"

private const val SERVER_URL = "http://localhost/auth"

private const val CLIENT_ID = "client"

private const val CLIENT_SECRET = "secret"

@UnitTest
@Suppress("MaxLineLength")
class KeycloakRefreshTokenManagerRepositoryTest {

    private val faker = Faker()
    private val webClient = mockk<WebClient>(relaxed = true)
    private val requestBodyUriSpec = mockk<WebClient.RequestBodyUriSpec>()
    private val responseSpec = mockk<WebClient.ResponseSpec>()
    private val applicationSecurityProperties = mockk<ApplicationSecurityProperties>()
    private lateinit var keycloakRefreshTokenManagerRepository: KeycloakRefreshTokenManagerRepository
    private val oAuth2 = mockk<ApplicationSecurityProperties.OAuth2>()
    private val expectedAccessToken =
        AccessToken(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
            3600L,
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
            3600L,
            "bearer",
            0,
            "session",
            "email profile",
        )
    private lateinit var expectedAccessTokenResponse: AccessTokenResponse

    @BeforeEach
    fun setUp() {
        every { applicationSecurityProperties.oauth2 } returns oAuth2
        every { oAuth2.serverUrl } returns SERVER_URL
        every { oAuth2.realm } returns REALM
        every { oAuth2.clientId } returns CLIENT_ID
        every { oAuth2.clientSecret } returns CLIENT_SECRET

        expectedAccessTokenResponse = mockk<AccessTokenResponse> {
            every { token } returns expectedAccessToken.token
            every { expiresIn } returns expectedAccessToken.expiresIn
            every { refreshToken } returns expectedAccessToken.refreshToken
            every { refreshExpiresIn } returns expectedAccessToken.refreshExpiresIn
            every { tokenType } returns expectedAccessToken.tokenType
            every { notBeforePolicy } returns expectedAccessToken.notBeforePolicy!!
            every { sessionState } returns expectedAccessToken.sessionState
            every { scope } returns expectedAccessToken.scope
        }
        // Mock the necessary WebClient call and AccessTokenResponse
        every { webClient.post() } returns requestBodyUriSpec
        every { requestBodyUriSpec.uri(any<URI>()) } returns requestBodyUriSpec
        every { requestBodyUriSpec.contentType(eq(MediaType.APPLICATION_FORM_URLENCODED)) } returns requestBodyUriSpec
        every { requestBodyUriSpec.body(any()) } returns requestBodyUriSpec
        every { requestBodyUriSpec.retrieve() } returns responseSpec
        every { responseSpec.bodyToMono(AccessTokenResponse::class.java) } returns Mono.just(expectedAccessTokenResponse)

        keycloakRefreshTokenManagerRepository = KeycloakRefreshTokenManagerRepository(applicationSecurityProperties)
        // add webClient to the repository by reflection
        val field = keycloakRefreshTokenManagerRepository::class.java.getDeclaredField("webClient")
        field.isAccessible = true
        field.set(keycloakRefreshTokenManagerRepository, webClient)
    }

    @Test
    fun `should refresh access token`() = runBlocking {
        val refreshToken = RefreshToken(faker.lorem().word())
        val accessToken = keycloakRefreshTokenManagerRepository.refresh(refreshToken)
        assertEquals(expectedAccessToken, accessToken)
    }

    @Test
    fun `should return exception when refresh access token`() = runBlocking {
        val refreshToken = RefreshToken(faker.lorem().word())

        // Simulate an error response when refreshing the access token
        val errorResponse = mockk<WebClientResponseException> {
            every { statusCode } returns HttpStatus.BAD_REQUEST
        }
        every { responseSpec.bodyToMono(AccessTokenResponse::class.java) } throws errorResponse

        // Use assertThrows to verify that an exception is thrown
        val exception = assertThrows<WebClientResponseException> {
            keycloakRefreshTokenManagerRepository.refresh(refreshToken)
        }

        assertEquals(HttpStatus.BAD_REQUEST, exception.statusCode)
    }
}
