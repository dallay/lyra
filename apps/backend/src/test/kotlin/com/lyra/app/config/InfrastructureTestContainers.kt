package com.lyra.app.config

import com.lyra.IntegrationTest
import com.lyra.app.authentication.domain.AccessToken
import com.lyra.app.authentication.infrastructure.mapper.AccessTokenResponseMapper.toAccessToken
import dasniko.testcontainers.keycloak.KeycloakContainer
import java.net.URI
import java.net.URISyntaxException
import org.junit.jupiter.api.BeforeAll
import org.keycloak.representations.AccessTokenResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.Network
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

private const val WEB_PORT = 6080

@Testcontainers
@IntegrationTest
abstract class InfrastructureTestContainers {
    @Value("\${testing.security.username}")
    protected lateinit var testUsername: String

    @Value("\${testing.security.password}")
    protected lateinit var testPassword: String

    init {
        log.info("Starting infrastructure... \uD83D\uDE80")
        startInfrastructure()
    }

    protected fun getAccessToken(
        username: String = testUsername,
        password: String = testPassword
    ): AccessToken? =
        try {
            val authServerUrl = removeLastSlash(keycloakContainer.authServerUrl)
            val openIdConnectToken = "protocol/openid-connect/token"
            val authorizationURI = URI("$authServerUrl/realms/$REALM/$openIdConnectToken")
            val webclient = WebClient.builder().build()
            val formData: MultiValueMap<String, String> = LinkedMultiValueMap()
            formData.add("grant_type", "password")
            formData.add("client_id", CLIENT_ID)
            formData.add("username", username)
            formData.add("password", password)

            val result = webclient.post()
                .uri(authorizationURI)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(AccessTokenResponse::class.java)
                .block()

            result?.toAccessToken()
        } catch (e: URISyntaxException) {
            log.error("Can't obtain an access token from Keycloak!", e)
            null
        }

    companion object {
        private val log = LoggerFactory.getLogger(InfrastructureTestContainers::class.java)
        private const val ADMIN_USER: String = "admin"
        private const val ADMIN_PASSWORD: String = "secret"
        private const val REALM: String = "lyra"
        private const val CLIENT_ID: String = "web_app"
        private const val ADMIN_CLI = "admin-cli"
        private const val ADMIN_REALM = "master"
        private val ports = arrayOf(3025, 3110, 3143, 3465, 3993, 3995, WEB_PORT)
        private val NETWORK: Network = Network.newNetwork()

        @JvmStatic
        private val keycloakContainer: KeycloakContainer =
            KeycloakContainer("keycloak/keycloak:24.0")
                .withRealmImportFile("keycloak/demo-realm-test.json")
                .withAdminUsername(ADMIN_USER)
                .withAdminPassword(ADMIN_PASSWORD)
                .withCreateContainerCmdModifier { cmd -> cmd.withName("keycloak-tests") }
                .withNetwork(NETWORK)
                .withReuse(true)

        @JvmStatic
        private val greenMailContainer: GenericContainer<*> = GenericContainer<Nothing>(
            DockerImageName.parse("greenmail/standalone:2.0.0"),
        ).apply {
            withEnv(
                "GREENMAIL_OPTS",
                "-Dgreenmail.setup.test.smtp -Dgreenmail.hostname=0.0.0.0",
            )
            waitingFor(Wait.forLogMessage(".*Starting GreenMail standalone.*", 1))
            withExposedPorts(*ports)
            withCreateContainerCmdModifier { cmd -> cmd.withName("greenmail-tests") }
            withNetwork(NETWORK)
            withReuse(true)
        }

        @JvmStatic
        @DynamicPropertySource
        fun registerResourceServerIssuerProperty(registry: DynamicPropertyRegistry) {
            registerKeycloakProperties(registry)
            registerMailProperties(registry)
        }

        private fun registerMailProperties(registry: DynamicPropertyRegistry) {
            log.info("Registering Mail Properties \uD83D\uDCE8")
            registry.add("spring.mail.host") { greenMailContainer.host }
            registry.add("spring.mail.port") { greenMailContainer.firstMappedPort }
        }

        private fun registerKeycloakProperties(registry: DynamicPropertyRegistry) {
            log.info("Registering Keycloak Properties \uD83D\uDC77\uD83C\uDFFB\u200D♂\uFE0F")
            startInfrastructure()
            val authServerUrl = removeLastSlash(keycloakContainer.authServerUrl)
            registry.add(
                "spring.security.oauth2.resourceserver.jwt.issuer-uri",
            ) { authServerUrl }

            registry.add(
                "application.security.oauth2.base-url",
            ) { authServerUrl }

            registry.add(
                "application.security.oauth2.server-url",
            ) { authServerUrl }

            registry.add(
                "application.security.oauth2.issuer-uri",
            ) { "$authServerUrl/realms/$REALM" }

            registry.add(
                "application.security.oauth2.realm",
            ) { REALM }

            registry.add(
                "application.security.oauth2.client-id",
            ) { CLIENT_ID }

            registry.add(
                "application.security.oauth2.admin-client-id",
            ) { ADMIN_CLI }

            registry.add(
                "application.security.oauth2.admin-realm",
            ) { ADMIN_REALM }

            registry.add(
                "application.security.oauth2.admin-username",
            ) { ADMIN_USER }

            registry.add(
                "application.security.oauth2.admin-password",
            ) { ADMIN_PASSWORD }
        }

        private fun removeLastSlash(url: String): String {
            if (url.length > 1 && url.endsWith("/")) {
                return url.substring(0, url.length - 1)
            }
            return url
        }

        @BeforeAll
        @JvmStatic
        fun startInfrastructure() {
            if (!keycloakContainer.isRunning) {
                log.info("Keycloak Containers Start \uD83D\uDE80")
                keycloakContainer.start()
            }
            if (!greenMailContainer.isRunning) {
                log.info("Green Mail Containers Start \uD83D\uDCE8")
                greenMailContainer.start()
            }
        }
    }
}
