package com.lyra.app.authentication.infrastructure

import org.mockito.Mockito.mock
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder

private const val DOMAIN = "lyra.com"
private const val PROTOCOL = "https"
private const val BASIC_URL = "$PROTOCOL://$DOMAIN"

/**
 * Esta clase permite ejecutar pruebas unitarias e integradas sin un IdP.
 */
@TestConfiguration
@Import(OAuth2Configuration::class)
class TestSecurityConfiguration {
    @Bean
    fun clientRegistration(): ClientRegistration = clientRegistrationBuilder().build()

    @Bean
    fun clientRegistrationRepository(
        clientRegistration: ClientRegistration
    ): ReactiveClientRegistrationRepository = InMemoryReactiveClientRegistrationRepository(clientRegistration)

    private fun clientRegistrationBuilder(): ClientRegistration.Builder {
        val metadata: MutableMap<String, Any> = HashMap()
        metadata["end_session_endpoint"] = "$BASIC_URL/logout"
        return ClientRegistration
            .withRegistrationId("oidc")
            .issuerUri("{baseUrl}")
            .redirectUri("{baseUrl}/{action}/oauth2/code/{registrationId}")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .scope("read:user")
            .authorizationUri("$BASIC_URL/login/oauth/authorize")
            .tokenUri("$BASIC_URL/login/oauth/access_token")
            .jwkSetUri("$BASIC_URL/oauth/jwk")
            .userInfoUri("$PROTOCOL://api.$DOMAIN/user")
            .providerConfigurationMetadata(metadata)
            .userNameAttributeName("id")
            .clientName("Client Name")
            .clientId("client-id")
            .clientSecret("client-secret")
    }

    @Bean
    fun jwtDecoder(): ReactiveJwtDecoder = mock(ReactiveJwtDecoder::class.java)

    @Suppress("MaxLineLength", "ParameterListWrapping")
    @Bean
    fun authorizedClientService(
        clientRegistrationRepository: ClientRegistrationRepository
    ): OAuth2AuthorizedClientService =
        InMemoryOAuth2AuthorizedClientService(
            clientRegistrationRepository,
        )
}
