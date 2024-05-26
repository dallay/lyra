package com.lyra.app.config.springdoc

import com.lyra.app.authentication.infrastructure.ApplicationSecurityProperties
import com.lyra.common.domain.Generated
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class for SpringDoc.
 *
 * This class is responsible for configuring the SpringDoc library
 * and provides beans for generating OpenAPI documentation.
 * @see OpenAPI for more information about the OpenAPI specification.
 * @see GroupedOpenApi for more information about the SpringDoc library.
 * @see Bean for more information about the annotation.
 * @see Configuration for more information about the annotation.
 * @see Value for more information about the annotation.
 * @see Generated for more information about the annotation.
 * @see SpringDocConfiguration for more information about the configuration class.
 *
 */
@Configuration
@Generated(reason = "Not testing technical configuration")
class SpringDocConfiguration(private val applicationSecurityProperties: ApplicationSecurityProperties) {

    @Value("\${application.version:undefined}")
    private val version: String? = null

    @Value("\${application.name:Lyra}")
    private val name: String? = null

    @Value("\${application.description:undefined}")
    private val description: String? = null

    @Bean
    fun appOpenAPI(): OpenAPI {
        val openIdConnectUrl = "${applicationSecurityProperties.oauth2.issuerUri}/.well-known/openid-configuration"
        return OpenAPI()
            .info(swaggerInfo())
            .externalDocs(swaggerExternalDoc())
            .components(
                io.swagger.v3.oas.models.Components()
                    .addSecuritySchemes(
                        "Keycloak",
                        SecurityScheme()
                            .type(SecurityScheme.Type.OPENIDCONNECT)
                            .scheme("bearer")
                            .`in`(SecurityScheme.In.HEADER)
                            .openIdConnectUrl(openIdConnectUrl),
                    ),
            )
            .addSecurityItem(SecurityRequirement().addList("Keycloak"))
    }

    private fun swaggerInfo(): Info {
        val contact = Contact().name("Yuniel Acosta").url("www.yunielacosta.com").email("ylaz@gft.com")
        return Info().title("${name?.uppercase()} API").description(description).version(version)
            .contact(contact)
            .license(License().name("No license").url(""))
    }

    private fun swaggerExternalDoc(): ExternalDocumentation =
        ExternalDocumentation().description("Project Documentation").url("")

    @Bean
    fun appAllOpenAPI(): GroupedOpenApi =
        GroupedOpenApi.builder().group("all").pathsToMatch("/api/**").build()
}
