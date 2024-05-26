package com.lyra.app.users.infrastructure.persistence.keycloak

import com.lyra.app.authentication.infrastructure.ApplicationSecurityProperties
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private const val CONNECTION_POOL_SIZE = 10

/**
 * Configuration class for Keycloak integration.
 *
 * This class is responsible for creating and configuring the Keycloak instance based on the provided
 * application security properties.
 *
 * The configuration class is annotated with @Configuration to indicate that it is a configuration class,
 * and @EnableConfigurationProperties(ApplicationSecurityProperties::class) to enable the use of application
 * security properties.
 *
 * The Keycloak instance is created and configured as a bean using the @Bean annotation on the keycloak() method.
 * The application security properties are injected as a parameter to the method, allowing access to the necessary
 * configuration values.
 *
 * The KeycloakBuilder from the Keycloak library is used to build the Keycloak instance. The server URL, realm,
 * grant type, username, password, and client ID are set using the values from the application security properties.
 *
 * The resteasyClient is also configured with a connection pool size using the ResteasyClientBuilderImpl. The
 * connection pool size is set to CONNECTION_POOL_SIZE, which should be defined elsewhere in the code.
 *
 * The Keycloak instance is then built and returned as the result of the keycloak() method.
 *
 * @author acosta
 * @created 1/7/23
 *
 * @see ApplicationSecurityProperties for more information about the ApplicationSecurityProperties
 * @see Configuration for more information about the Configuration annotation
 * @see EnableConfigurationProperties for more information about the EnableConfigurationProperties annotation
 *
 */
@Configuration
@EnableConfigurationProperties(ApplicationSecurityProperties::class)
class KeycloakConfiguration {

    /**
     * Creates and configures a Keycloak instance based on the provided application security properties.
     *
     * @param applicationSecurityProperties the application security properties to use for configuring
     * the Keycloak instance
     * @return the Keycloak instance
     */
    @Bean
    fun keycloak(applicationSecurityProperties: ApplicationSecurityProperties): Keycloak =
        KeycloakBuilder.builder()
            .serverUrl(applicationSecurityProperties.oauth2.serverUrl)
            .realm(applicationSecurityProperties.oauth2.adminRealm)
            .grantType(OAuth2Constants.PASSWORD)
            .username(applicationSecurityProperties.oauth2.adminUsername)
            .password(applicationSecurityProperties.oauth2.adminPassword)
            .clientId(applicationSecurityProperties.oauth2.adminClientId)
            .resteasyClient(
                ResteasyClientBuilderImpl()
                    .connectionPoolSize(CONNECTION_POOL_SIZE).build(),
            )
            .build()
}
