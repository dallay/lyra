package com.lyra.app.authentication.infrastructure.persistence.keycloak

import com.lyra.app.authentication.domain.AccessToken
import com.lyra.app.authentication.domain.RefreshToken
import com.lyra.app.authentication.domain.RefreshTokenManager
import com.lyra.app.authentication.domain.UserRefreshTokenException
import com.lyra.app.authentication.infrastructure.ApplicationSecurityProperties
import com.lyra.app.authentication.infrastructure.mapper.AccessTokenResponseMapper.toAccessToken
import java.net.URI
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.keycloak.representations.AccessTokenResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

/**
 * Represents a [RefreshTokenManager] that is responsible for refreshing the access token of the user.
 */
@Service
class KeycloakRefreshTokenManagerRepository(
    private val applicationSecurityProperties: ApplicationSecurityProperties
) : RefreshTokenManager {
    private val webClient: WebClient = WebClient.builder().build()
    private val authorizationURI = URI(
        constructOpenIdConnectTokenUrl(),
    )

    private fun constructOpenIdConnectTokenUrl(
        realm: String = applicationSecurityProperties.oauth2.realm,
        serverUrl: String = applicationSecurityProperties.oauth2.serverUrl
    ) = "$serverUrl/realms/$realm/$OPENID_CONNECT_TOKEN"

    /**
     * Refreshes the access token of the user.
     *
     * @param refreshToken the refresh token of the user
     * @return the access token of the user
     */
    override suspend fun refresh(refreshToken: RefreshToken): AccessToken {
        log.debug("Refreshing access token for user with refresh token")
        val formData: MultiValueMap<String, String> = LinkedMultiValueMap()
        formData.add("grant_type", "refresh_token")
        formData.add("client_id", applicationSecurityProperties.oauth2.clientId)
        formData.add("refresh_token", refreshToken.value)
        return webClient.post()
            .uri(authorizationURI)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData(formData))
            .retrieve()
            .bodyToMono(AccessTokenResponse::class.java)
            .map { it.toAccessToken() }
            .onErrorMap {
                UserRefreshTokenException("Could not refresh access token", it)
            }
            .awaitSingleOrNull() ?: throw UserRefreshTokenException("Could not refresh access token")
    }

    companion object {
        private const val OPENID_CONNECT_TOKEN = "protocol/openid-connect/token"
        private val log = LoggerFactory.getLogger(KeycloakRefreshTokenManagerRepository::class.java)
    }
}
