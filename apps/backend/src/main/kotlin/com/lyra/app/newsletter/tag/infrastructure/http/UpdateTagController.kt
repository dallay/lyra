package com.lyra.app.newsletter.tag.infrastructure.http

import com.lyra.app.AppConstants.Paths.API
import com.lyra.app.AppConstants.Paths.TAG
import com.lyra.app.newsletter.tag.application.update.UpdateTagCommand
import com.lyra.app.newsletter.tag.infrastructure.http.request.UpdateTagRequest
import com.lyra.common.domain.bus.Mediator
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @created 22/9/24
 */
@RestController
@RequestMapping(value = [API], produces = ["application/vnd.api.v1+json"])
class UpdateTagController(
    mediator: Mediator,
) : ApiController(mediator) {
    @Operation(summary = "Update a tag")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Created"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @PutMapping("$TAG/{tagId}/update")
    suspend fun update(
        @PathVariable organizationId: String,
        @PathVariable tagId: String,
        @Validated @RequestBody request: UpdateTagRequest
    ): ResponseEntity<String> {
        log.debug(
            "Updating tag with data: {}",
            sanitizeAndJoinPathVariables(organizationId, tagId, request.toString()),
        )
        dispatch(
            UpdateTagCommand(
                tagId,
                request.name,
                request.color,
                organizationId,
                request.subscribers,
            ),
        )
        return ResponseEntity.ok("Tag updated")
    }

    companion object {
        private val log = LoggerFactory.getLogger(UpdateTagController::class.java)
    }
}
