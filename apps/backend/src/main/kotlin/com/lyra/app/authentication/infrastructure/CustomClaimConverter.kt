package com.lyra.app.authentication.infrastructure

import com.fasterxml.jackson.databind.node.ObjectNode
import com.lyra.app.authentication.infrastructure.ClaimExtractor.CLAIM_APPENDERS
import com.lyra.app.authentication.infrastructure.ClaimExtractor.SUB
import com.lyra.common.domain.Memoizers
import org.springframework.core.convert.converter.Converter
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

/**
 * CustomClaimConverter is a class that implements the [Converter] interface to convert a map of claims
 * to a modified map of claims by appending custom claims from a user object.
 *
 * @param registration The client registration details.
 * @param webClient The WebClient instance used to make HTTP requests.
 * @param token The token used for authentication.
 * @since 1.0.0
 */
class CustomClaimConverter(
    private val registration: ClientRegistration,
    private val webClient: WebClient,
    private val token: String
) : Converter<Map<String, Any>, Map<String, Any>> {

    private val delegate = MappedJwtClaimSetConverter.withDefaults(emptyMap())
    private val users: (SubAttributes) -> Mono<ObjectNode> = Memoizers.of { _ ->
        loadUser()
    }

    /**
     * Appends custom claims from the user object to the claim map.
     *
     * @param claim The claim map to append the custom claims to.
     * @param user The user object containing the custom claims.
     * @return The updated claim map after appending the custom claims.
     */
    private fun appendCustomClaim(claim: MutableMap<String, Any>, user: ObjectNode): MutableMap<String, Any> {
        CLAIM_APPENDERS.stream().forEach {
            it.append(claim, user)
        }.apply { return claim }
    }

    /**
     * Converts the given claims map into a new map with additional custom claims.
     *
     * @param claims The original claims a map to be converted.
     * @return The converted claims map with additional custom claims.
     */
    override fun convert(claims: Map<String, Any>): Map<String, Any> {
        log.debug("Starting claim conversion")
        val convertedClaims = delegate.convert(claims)?.toMutableMap() ?: mutableMapOf()
        val userMono = getUser(claims)
        userMono
            .subscribe(
                { user ->
                    Mono.justOrEmpty(appendCustomClaim(convertedClaims, user)).defaultIfEmpty(convertedClaims)
                },
                { error -> log.error("Error getting user information: {}", error) },
            )
        return convertedClaims
    }

    /**
     * Retrieves user information based on the claims provided.
     *
     * @param claims A map containing claims associated with the user.
     * @return A Mono emitting an ObjectNode representing the user information,
     *         or an empty Mono if the 'sub' claim is not present or is not a String.
     */
    private fun getUser(claims: Map<String, Any>): Mono<ObjectNode> {
        val sub = claims[SUB] as? String ?: return Mono.empty()
        val subAttributes = SubAttributes(sub)
        return users(subAttributes)
    }

    /**
     * Loads the user information from the provider's user info endpoint.
     *
     * @return A Mono that emits the user information as a JSON object.
     */
    private fun loadUser(): Mono<ObjectNode> {
        return getToken().flatMap { token ->
            webClient.get()
                .uri(registration.providerDetails.userInfoEndpoint.uri)
                .headers { it.setBearerAuth(token) }
                .retrieve()
                .bodyToMono(ObjectNode::class.java)
        }
    }

    /**
     * Retrieves the token.
     *
     * @return A Mono that emits the token as a String.
     */
    private fun getToken(): Mono<String> = Mono.just(token)

    /**
     * Represents a class that holds sub attributes.
     * @property sub The sub attribute.
     */
    private data class SubAttributes(val sub: String)

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(CustomClaimConverter::class.java)
    }
}
