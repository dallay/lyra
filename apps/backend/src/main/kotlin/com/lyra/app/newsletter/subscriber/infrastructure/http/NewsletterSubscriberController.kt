package com.lyra.app.newsletter.subscriber.infrastructure.http

import com.lyra.app.AppConstants.Paths.API
import com.lyra.app.AppConstants.Paths.SUBSCRIBER
import com.lyra.app.newsletter.subscriber.application.SubscribeNewsletterCommand
import com.lyra.app.newsletter.subscriber.infrastructure.http.request.SubscribeNewsletterRequest
import com.lyra.common.domain.bus.Mediator
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.net.URI
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [API], produces = ["application/vnd.api.v1+json"])
class NewsletterSubscriberController(
    mediator: Mediator,
) : ApiController(mediator) {
    @Operation(summary = "Subscribe to newsletter")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Created"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @PutMapping("$SUBSCRIBER/{subscriberId}")
    suspend fun subscribe(
        @PathVariable organizationId: String,
        @PathVariable subscriberId: String,
        @Validated @RequestBody request: SubscribeNewsletterRequest
    ): ResponseEntity<String> {
        log.debug(
            "Subscribing to newsletter with data: {}",
            sanitizeAndJoinPathVariables(subscriberId, request.toString()),
        )
        dispatch(
            SubscribeNewsletterCommand(
                subscriberId,
                request.email,
                request.firstname,
                request.lastname,
                request.attributes,
                organizationId,
            ),
        )

        return ResponseEntity.created(
            URI.create(
                "/api${
                    SUBSCRIBER.replace(
                        "{organizationId}",
                        organizationId,
                    )
                }",
            ),
        ).build()
    }

    companion object {
        private val log = LoggerFactory.getLogger(NewsletterSubscriberController::class.java)
    }
}
