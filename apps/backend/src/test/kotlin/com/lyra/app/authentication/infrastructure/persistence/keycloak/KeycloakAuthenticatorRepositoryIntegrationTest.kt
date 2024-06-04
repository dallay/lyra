package com.lyra.app.authentication.infrastructure.persistence.keycloak

import com.lyra.IntegrationTest
import com.lyra.app.CredentialGenerator
import com.lyra.app.authentication.domain.UserAuthenticationException
import com.lyra.app.authentication.domain.UserAuthenticator
import com.lyra.app.authentication.domain.Username
import com.lyra.app.config.InfrastructureTestContainers
import com.lyra.app.users.domain.User
import com.lyra.app.users.domain.UserCreator
import kotlinx.coroutines.runBlocking
import net.datafaker.Faker
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@IntegrationTest
class KeycloakAuthenticatorRepositoryIntegrationTest : InfrastructureTestContainers() {
    // this user is created by default in Keycloak container (see demo-realm-test.json)
    private val email = "john.doe@lyra.com"
    private val username = "john.doe"
    private val password = "S3cr3tP@ssw0rd*123"

    private val faker = Faker()

    @Autowired
    private lateinit var userCreator: UserCreator

    @Autowired
    private lateinit var userAuthenticator: UserAuthenticator

    @BeforeEach
    fun setUp() {
        startInfrastructure()
    }

    @Test
    fun `should authenticate a user by email`(): Unit = runBlocking {
        val username = Username(email)
        val credential = CredentialGenerator.generate(password)
        val accessToken = userAuthenticator.authenticate(username, credential)
        assertNotNull(accessToken)
        assertNotNull(accessToken.token)
        assertNotNull(accessToken.refreshToken)
        assertNotNull(accessToken.expiresIn)
        assertNotNull(accessToken.refreshExpiresIn)
        assertNotNull(accessToken.tokenType)
        assertNotNull(accessToken.scope)
        assertNotNull(accessToken.notBeforePolicy)
        assertNotNull(accessToken.sessionState)
    }

    @Test
    fun `should authenticate a user by username`(): Unit = runBlocking {
        val username = Username(username)
        val credential = CredentialGenerator.generate(password)
        val accessToken = userAuthenticator.authenticate(username, credential)
        assertNotNull(accessToken)
        assertNotNull(accessToken.token)
        assertNotNull(accessToken.refreshToken)
        assertNotNull(accessToken.expiresIn)
        assertNotNull(accessToken.refreshExpiresIn)
        assertNotNull(accessToken.tokenType)
        assertNotNull(accessToken.scope)
        assertNotNull(accessToken.notBeforePolicy)
        assertNotNull(accessToken.sessionState)
    }

    @Test
    fun `should throw exception when user is not found`(): Unit = runBlocking {
        val username = Username("not.found")
        val credential = CredentialGenerator.generate(password)
        val exception = assertThrows(UserAuthenticationException::class.java) {
            runBlocking {
                userAuthenticator.authenticate(username, credential)
            }
        }
        assertEquals("Invalid account. User probably hasn't verified email.", exception.message)
    }

    @Test
    fun `should throw exception when password is wrong`(): Unit = runBlocking {
        val username = Username(email)
        val credential = CredentialGenerator.generate("${password}wrong")
        val exception = assertThrows(UserAuthenticationException::class.java) {
            runBlocking {
                userAuthenticator.authenticate(username, credential)
            }
        }
        assertEquals("Invalid account. User probably hasn't verified email.", exception.message)
    }

    @Test
    fun `should throw exception when user is not verified`(): Unit = runBlocking {
        val randomEmail = faker.internet().emailAddress()
        val randomPassword = faker.internet().password(8, 80, true, true, true) + "1Aa@"
        val user = User.create(
            email = randomEmail,
            firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            password = randomPassword,
        )
        val createdUser = userCreator.create(user)
        assertNotNull(createdUser)
        val username = Username(randomEmail)
        val credential = CredentialGenerator.generate(randomPassword)
        val exception = assertThrows(UserAuthenticationException::class.java) {
            runBlocking {
                userAuthenticator.authenticate(username, credential)
            }
        }
        assertEquals("Invalid account. User probably hasn't verified email.", exception.message)
    }
}
