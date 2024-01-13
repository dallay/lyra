package com.lyra.app.newsletter.infrastructure.http

import com.lyra.app.newsletter.application.SubscribeNewsletterCommand
import com.lyra.app.newsletter.infrastructure.http.request.SubscribeNewsletterRequest
import com.lyra.common.domain.bus.Mediator
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import java.net.URI
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class NewsletterSubscriberController(
    mediator: Mediator,
) : ApiController(mediator) {
    @Operation(summary = "Subscribe to newsletter")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Created"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @PutMapping("/newsletter/subscribers/{id}")
    suspend fun subscribe(
        @PathVariable id: String,
        @Valid @RequestBody request: SubscribeNewsletterRequest
    ): ResponseEntity<String> {

        dispatch(
            SubscribeNewsletterCommand(
                id,
                request.email,
                request.firstname,
                request.lastname,
            ),
        )

        return ResponseEntity.created(URI.create("/api/newsletter/subscribers/$id")).build()
    }
}
