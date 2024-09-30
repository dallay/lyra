package com.lyra.app.users.infrastructure.persistence.keycloak

import com.lyra.app.authentication.infrastructure.ApplicationSecurityProperties
import com.lyra.app.users.domain.User
import com.lyra.app.users.domain.UserCreator
import com.lyra.app.users.domain.UserException
import com.lyra.app.users.domain.UserId
import com.lyra.app.users.domain.UserStoreException
import com.lyra.common.domain.error.BusinessRuleValidationException
import com.lyra.common.domain.vo.credential.Credential
import com.lyra.common.domain.vo.credential.CredentialException
import jakarta.ws.rs.ClientErrorException
import jakarta.ws.rs.WebApplicationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class KeycloakRepository(
    private val applicationSecurityProperties: ApplicationSecurityProperties,
    private val keycloak: Keycloak
) : UserCreator {

    private val keycloakRealm by lazy {
        keycloak.realm(applicationSecurityProperties.oauth2.realm)
    }

    override suspend fun create(user: User): User {
        log.info("Saving user with email: {}", user.email.value.replace("\n", "").replace("\r", ""))

        val message = "Error creating user with email: ${user.email.value}"

        return withContext(Dispatchers.IO) {
            try {
                val password =
                    user.credentials.firstOrNull()?.credentialValue
                        ?: Credential.generateRandomCredentialPassword()
                val credentialRepresentation = CredentialRepresentation().apply {
                    type = CredentialRepresentation.PASSWORD
                    value = password
                }

                if (checkIfUserAlreadyExists(user)) {
                    val errorMessage =
                        "User with email: ${user.email.value} or username: ${user.username.value} already exists."
                    throw UserStoreException(errorMessage.trimIndent())
                } else {
                    log.debug(
                        "Trying to create user with email: {} and username: {}",
                        user.email.value.replace("\n", "").replace("\r", ""),
                        user.username.value.replace("\n", "").replace("\r", ""),
                    )
                    val userRepresentation = getUserRepresentation(user, credentialRepresentation)
                    userRepresentation.username = user.username.value
                    val response = keycloakRealm.users().create(userRepresentation)
                    val userId = response.location.path.replace(".*/([^/]+)$".toRegex(), "$1")
                    user.copy(id = UserId(userId))
                }
            } catch (exception: BusinessRuleValidationException) {
                log.error(
                    "Error creating user with email: {} and username: {}",
                    user.email.value.replace("\n", "").replace("\r", ""),
                    user.username.value.replace("\n", "").replace("\r", ""),
                    exception,
                )
                when (exception) {
                    is UserStoreException -> throw exception
                    is CredentialException -> throw UserStoreException(message, exception)
                    is UserException -> throw UserStoreException(message, exception)
                    else -> throw UserStoreException(message, exception)
                }
            } catch (exception: ClientErrorException) {
                log.error("Error creating user with email: {}", user.email.value.replace("\n", "").replace("\r", ""), exception)
                throw UserStoreException(message, exception)
            }
        }
    }

    private suspend fun checkIfUserAlreadyExists(user: User): Boolean {
        return withContext(Dispatchers.IO) {
            val userByEmail = getUserByEmail(user.email.value)
            val userByUsername = getUserByUsername(user.username.value)
            userByEmail != null || userByUsername != null
        }
    }

    private fun getUserRepresentation(
        user: User,
        credentialRepresentation: CredentialRepresentation
    ) = UserRepresentation().apply {
        username = user.username.value
        email = user.email.value
        firstName = user.name?.firstName?.value
        lastName = user.name?.lastName?.value
        isEnabled = true
        groups = listOf(USER_GROUP_NAME)
        credentials = listOf(credentialRepresentation)
    }

    private fun getUserByEmail(email: String): UserRepresentation? =
        keycloakRealm.users().searchByEmail(email, true).firstOrNull()

    private fun getUserByUsername(username: String): UserRepresentation? =
        keycloakRealm.users().searchByUsername(username, true).firstOrNull()

    suspend fun verify(userId: String) {
        log.info("Verifying user with id: {}", userId)
        try {
            withContext(Dispatchers.IO) {
                keycloakRealm.users()[userId].sendVerifyEmail()
            }
        } catch (_: WebApplicationException) {
            log.error("Error sending email verification to user with id: {}", userId)
        }
    }

    companion object {
        private const val USER_GROUP_NAME = "Users"
        private val log = LoggerFactory.getLogger(KeycloakRepository::class.java)
    }
}
