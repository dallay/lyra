package com.lyra.app.newsletter.subscriber.infrastructure.http

import com.lyra.app.AppConstants.Paths.API
import com.lyra.app.AppConstants.Paths.SUBSCRIBER
import com.lyra.app.newsletter.subscriber.application.SubscribersResponse
import com.lyra.app.newsletter.subscriber.application.search.email.AllSubscribersByEmailQuery
import com.lyra.common.domain.bus.Mediator
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [API], produces = ["application/vnd.api.v1+json"])
@Validated
class GetAllSubscribersByEmailController(
    mediator: Mediator
) : ApiController(mediator) {

    @Operation(summary = "Get all subscribers by email")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Success"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @GetMapping("$SUBSCRIBER/find-all-by-emails")
    @ResponseBody
    suspend fun getAllByEmail(
        @PathVariable organizationId: String,
        @RequestParam @Valid emails: Set<@Email(message = "Invalid email format") String>
    ): ResponseEntity<SubscribersResponse> {
        log.debug(
            "Getting all subscribers by emails: {}",
            sanitizeAndJoinPathVariables(organizationId, emails.joinToString()),
        )
        val response = ask(
            AllSubscribersByEmailQuery(
                organizationId,
                emails,
            ),
        )
        return ResponseEntity.ok(response)
    }

    companion object {
        private val log = LoggerFactory.getLogger(GetAllSubscribersByEmailController::class.java)
    }
}
