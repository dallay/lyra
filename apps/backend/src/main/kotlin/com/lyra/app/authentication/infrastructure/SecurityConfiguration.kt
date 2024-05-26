package com.lyra.app.authentication.infrastructure

import com.lyra.app.authentication.domain.Role
import com.lyra.app.authentication.infrastructure.filter.CookieCsrfFilter
import com.lyra.common.domain.Generated
import java.time.Duration
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientSsl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtValidators
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestAttributeHandler
import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient

@Suppress("MaxLineLength")
private const val POLICY =
    "camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()"

/**
 * Configuration class for setting up security in a Spring WebFlux application.
 *
 * This class is responsible for configuring the security settings in the application.
 * It also configures the security filter chain that will be used to protect the application.
 * @param applicationSecurityProperties the application security properties
 * @since 1.0.0
 * @see EnableWebFluxSecurity for enabling Spring Security in a WebFlux application
 * @see EnableReactiveMethodSecurity for enabling Spring Security method security in a WebFlux application
 * @see WebSecurityCustomizer for customizing the WebSecurity configuration
 * @see SecurityWebFilterChain for configuring the security filter chain
 * @see ServerHttpSecurity for configuring the security filter chain
 * @see ReactiveClientRegistrationRepository for managing OAuth 2.0 Client Registration
 * @see ReactiveJwtDecoder for decoding a JSON Web Token (JWT) from a Bearer Token request
 * @see WebClient for performing HTTP requests
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfiguration(
    val applicationSecurityProperties: ApplicationSecurityProperties
) {
    @Value("\${spring.security.oauth2.client.provider.oidc.issuer-uri}")
    private val issuerUri: String? = null

    /**
     * Returns a [CorsConfigurationSource] object that is configured based on the properties defined in
     * the applicationSecurityProperties. This method is annotated with @Bean, indicating that it should be
     * treated as a bean and managed by Spring framework.
     *
     * @return A [CorsConfigurationSource] object that is configured based on the applicationSecurityProperties.
     */
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = applicationSecurityProperties.cors.allowedOrigins
        configuration.allowedMethods = applicationSecurityProperties.cors.allowedMethods
        configuration.allowedHeaders = applicationSecurityProperties.cors.allowedHeaders
        configuration.exposedHeaders = applicationSecurityProperties.cors.exposedHeaders
        configuration.allowCredentials = applicationSecurityProperties.cors.allowCredentials
        configuration.maxAge = applicationSecurityProperties.cors.maxAge
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    /**
     * Builds a SecurityWebFilterChain for the provided ServerHttpSecurity instance.
     *
     * @param http The ServerHttpSecurity instance to configure the filter chain.
     * @return The configured SecurityWebFilterChain.
     */
    @Bean
    fun filterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        // @formatter:off
        return http
            .securityMatcher(
                serverWebExchangeMatcher(),
            )
            .csrf {
                    csrf ->
                csrf
                    .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
                    .csrfTokenRequestHandler(ServerCsrfTokenRequestAttributeHandler())
            }
            .cors {
                    cors ->
                cors.configurationSource(corsConfigurationSource())
            }
            .addFilterAt(CookieCsrfFilter(), SecurityWebFiltersOrder.REACTOR_CONTEXT)
//            .addFilterAfter(SpaWebFilter(), SecurityWebFiltersOrder.HTTPS_REDIRECT)
            .headers {
                    headers ->
                headers.contentSecurityPolicy { applicationSecurityProperties.contentSecurityPolicy }
                headers.referrerPolicy {
                        referrerPolicy ->
                    referrerPolicy.policy(
                        ReferrerPolicyServerHttpHeadersWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN,
                    )
                }

                headers.permissionsPolicy { permissions -> permissions.policy(POLICY) }
            }.redirectToHttps(withDefaults())
            .authorizeExchange {
                    auth ->
                configureAuthorization(auth)
            }
            .oauth2Login(withDefaults())
            .oauth2Client(withDefaults())
            .oauth2ResourceServer {
                    oauth2 ->
                oauth2.jwt {
                        jwt ->
                    jwt.jwtAuthenticationConverter(authenticationConverter())
                }
            }
            .build()
        // @formatter:on
    }

    private fun configureAuthorization(auth: ServerHttpSecurity.AuthorizeExchangeSpec) {
        auth
            .pathMatchers("/").permitAll()
            .pathMatchers("/*.*").permitAll()
            .pathMatchers("/api/health-check").permitAll()
            .pathMatchers("/api/register").permitAll()
            .pathMatchers("/api/refresh-token").permitAll()
            .pathMatchers("/api/login").permitAll()
            .pathMatchers("/api/logout").permitAll()
            .pathMatchers("/swagger-ui/**").permitAll()
            .pathMatchers("/webjars/**").permitAll()
            .pathMatchers("/api-docs/**").permitAll()
            .pathMatchers("/swagger-ui.html").permitAll()
            .pathMatchers("/v3/api-docs/**").permitAll()
            .pathMatchers("/v3/api-docs.yaml").permitAll()
            .pathMatchers("/actuator/**").authenticated()
            .pathMatchers("/api/**").authenticated()
            .pathMatchers("/management/health").permitAll()
            .pathMatchers("/management/info").permitAll()
            .pathMatchers("/management/prometheus").permitAll()
            .pathMatchers("/management/**").hasAuthority(Role.ADMIN.key())
    }

    private fun serverWebExchangeMatcher() = NegatedServerWebExchangeMatcher(
        OrServerWebExchangeMatcher(
            ServerWebExchangeMatchers.pathMatchers(
                "/app/**",
                "/_app/**",
                "/i18n/**",
                "/img/**",
                "/content/**",
                "/swagger-ui/**",
                "/webjars/**",
                "/api-docs/**",
                "/v3/api-docs/**",
                "/test/**",
            ),
            ServerWebExchangeMatchers.pathMatchers(HttpMethod.OPTIONS, "/**"),
        ),
    )

    /**
     * Converts a Jwt token into a Mono of [AbstractAuthenticationToken],
     * using a [ReactiveJwtAuthenticationConverterAdapter].
     *
     * @return Converter<Jwt, Mono<AbstractAuthenticationToken>> the authentication converter.
     */
    fun authenticationConverter(): Converter<Jwt, Mono<AbstractAuthenticationToken>> {
        val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
        val jwtAuthenticationConverter = ReactiveJwtAuthenticationConverter()
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
            ReactiveJwtGrantedAuthoritiesConverterAdapter(jwtGrantedAuthoritiesConverter),
        )

        return jwtAuthenticationConverter
    }

    /**
     * Map authorities from "groups" or "roles" claim in ID Token.
     *
     * @return a [GrantedAuthoritiesMapper] that maps groups from the IdP to Spring Security Authorities.
     */
    @Bean
    fun userAuthoritiesMapper(): GrantedAuthoritiesMapper {
        return GrantedAuthoritiesMapper { authorities ->
            val mappedAuthorities = HashSet<GrantedAuthority>()

            authorities.forEach { authority ->
                // Check for OidcUserAuthority because Spring Security 5.2 returns
                // each scope as a GrantedAuthority, which we don't care about.
                if (authority is OidcUserAuthority) {
                    mappedAuthorities.addAll(Claims.extractAuthorityFromClaims(authority.userInfo.claims))
                }
            }
            mappedAuthorities
        }
    }

    /**
     * Creates a ReactiveJwtDecoder instance for decoding JWT tokens.
     *
     * @param clientRegistrationRepository The repository containing client registrations.
     * @return A ReactiveJwtDecoder instance.
     */
    @Bean
    @Generated(reason = "Only called with a valid client registration repository")
    fun jwtDecoder(
        clientRegistrationRepository: ReactiveClientRegistrationRepository,
        ssl: WebClientSsl
    ): ReactiveJwtDecoder {
        val jwtDecoder = NimbusReactiveJwtDecoder.withIssuerLocation(issuerUri).build()
        val audienceValidator: OAuth2TokenValidator<Jwt> =
            AudienceValidator(applicationSecurityProperties.oauth2.audience)
        val withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri)
        val withAudience: OAuth2TokenValidator<Jwt> = DelegatingOAuth2TokenValidator(withIssuer, audienceValidator)
        jwtDecoder.setJwtValidator(withAudience)

        val sslCertificate = ssl.fromBundle("keycloak")
        return ReactiveJwtDecoder { token ->
            jwtDecoder.decode(token)
                .flatMap { jwt ->
                    clientRegistrationRepository.findByRegistrationId("oidc")
                        .flatMap {
                            val webClient = WebClient.builder()
                                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                                .clientConnector(
                                    ReactorClientHttpConnector(
                                        HttpClient.create().responseTimeout(Duration.ofMillis(TIMEOUT.toLong())),
                                    ),
                                )
                                .baseUrl(it!!.providerDetails.issuerUri)
                                .apply(sslCertificate)
                                .build()
                            jwtDecoder.setClaimSetConverter(
                                CustomClaimConverter(
                                    it,
                                    webClient,
                                    token,
                                ),
                            )
                            Mono.just(jwt)
                        }
                }
        }
    }
    companion object {
        private const val TIMEOUT = 2000
    }
}
