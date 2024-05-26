package com.lyra.app.authentication.infrastructure

import java.time.Duration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultReactiveOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository

/**
 * Configuration class for OAuth2 management. It is annotated with @Configuration to indicate that it is a Spring
 * configuration class.
 * It is responsible for creating the [ReactiveOAuth2AuthorizedClientManager] bean.
 * @see ReactiveOAuth2AuthorizedClientManager
 * @see DefaultReactiveOAuth2AuthorizedClientManager
 * @see ReactiveOAuth2AuthorizedClientProviderBuilder
 * @see ServerOAuth2AuthorizedClientRepository
 * @see ReactiveClientRegistrationRepository
 */
@Configuration
class OAuth2Configuration {
    /**
     * Creates an instance of ReactiveOAuth2AuthorizedClientManager using the provided client registration
     * repository and authorized client repository.
     *
     * @param clientRegistrationRepository The reactive client registration repository.
     * @param authorizedClientRepository The server OAuth2 authorized client repository.
     * @return An instance of ReactiveOAuth2AuthorizedClientManager.
     */
    @Bean
    fun authorizedClientManager(
        clientRegistrationRepository: ReactiveClientRegistrationRepository,
        authorizedClientRepository: ServerOAuth2AuthorizedClientRepository
    ): ReactiveOAuth2AuthorizedClientManager {
        val authorizedClientManager = DefaultReactiveOAuth2AuthorizedClientManager(
            clientRegistrationRepository,
            authorizedClientRepository,
        )
        authorizedClientManager.setAuthorizedClientProvider(
            ReactiveOAuth2AuthorizedClientProviderBuilder
                .builder()
                .authorizationCode()
                .refreshToken { builder: ReactiveOAuth2AuthorizedClientProviderBuilder.RefreshTokenGrantBuilder ->
                    builder.clockSkew(
                        Duration.ofMinutes(1),
                    )
                }
                .clientCredentials()
                .build(),
        )
        return authorizedClientManager
    }
}
