package com.lyra.app.authentication.infrastructure.persistence.keycloak

import com.lyra.app.authentication.domain.AccessToken
import com.lyra.app.authentication.domain.UserAuthenticationException
import com.lyra.app.authentication.domain.UserAuthenticator
import com.lyra.app.authentication.domain.Username
import com.lyra.app.authentication.infrastructure.ApplicationSecurityProperties
import com.lyra.app.authentication.infrastructure.mapper.AccessTokenResponseMapper.toAccessToken
import com.lyra.common.domain.vo.credential.Credential
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.ClientErrorException
import jakarta.ws.rs.NotAuthorizedException
import org.keycloak.admin.client.KeycloakBuilder
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

/**
 * This class represents a repository for authenticating users using Keycloak. It implements
 * the [UserAuthenticator] interface, which defines the authenticate() method for user authentication.
 *
 * @property applicationSecurityProperties The properties for configuring the Keycloak authentication.
 *
 * @constructor Creates a new instance of KeycloakAuthenticatorRepository.
 * @param applicationSecurityProperties The properties for configuring the Keycloak authentication.
 * @created 31/7/23
 */
@Repository
class KeycloakAuthenticatorRepository(
    private val applicationSecurityProperties: ApplicationSecurityProperties
) : UserAuthenticator {

    /**
     * Creates a new [KeycloakBuilder] instance with password credentials.
     *
     * @param username The username for authentication.
     * @param password The password for authentication.
     * @return The [KeycloakBuilder] instance configured with the provided username and password.
     */
    private fun newKeycloakBuilderWithPasswordCredentials(
        username: String,
        password: String,
        grantType: String = "password"
    ): KeycloakBuilder = KeycloakBuilder.builder()
        .realm(applicationSecurityProperties.oauth2.realm)
        .serverUrl(applicationSecurityProperties.oauth2.serverUrl)
        .clientId(applicationSecurityProperties.oauth2.clientId)
        .clientSecret(applicationSecurityProperties.oauth2.clientSecret)
        .grantType(grantType)
        .username(username)
        .password(password)

    /**
     * Login a user with the given username and password.
     *
     * @param username the username of the user to be logged in
     * @param password the password of the user to be logged in
     * @return The [AccessToken] object containing the access token and other information.
     */
    override suspend fun authenticate(username: Username, password: Credential): AccessToken {
        log.info("Authenticating user with username: {}", username)
        return try {
            val keycloak = newKeycloakBuilderWithPasswordCredentials(username.value, password.value).build()
            val accessTokenResponse = keycloak.tokenManager().accessToken
            accessTokenResponse.toAccessToken().also { log.info("User authenticated successfully") }
        } catch (ex: ClientErrorException) {
            var message: String = ex.message ?: ""
            when (ex) {
                is NotAuthorizedException,
                is BadRequestException -> {
                    log.warn("Unable to authenticate user", ex)
                    message = "Invalid account. User probably hasn't verified email."
                }

                else -> log.error("Unable to authenticate user", ex)
            }
            throw UserAuthenticationException(message, ex)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(KeycloakAuthenticatorRepository::class.java)
    }
}
