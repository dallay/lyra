package com.lyra.app.newsletter.tag.infrastructure.http

import com.lyra.app.AppConstants.Paths.API
import com.lyra.app.AppConstants.Paths.TAG
import com.lyra.app.newsletter.tag.application.CreateTagCommand
import com.lyra.app.newsletter.tag.domain.TagColor
import com.lyra.app.newsletter.tag.infrastructure.http.request.CreateTagRequest
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

/**
 * REST controller for handling requests to create a new tag.
 *
 * @property mediator The mediator for dispatching commands.
 */
@RestController
@RequestMapping(value = [API], produces = ["application/vnd.api.v1+json"])
class CreateTagController(
    mediator: Mediator,
) : ApiController(mediator) {

    /**
     * Creates a new tag.
     *
     * @param tagId The unique identifier of the tag.
     * @param request The request containing the tag details.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @Operation(summary = "Create a new tag")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Created"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @PutMapping("$TAG/{tagId}")
    suspend fun create(
        @PathVariable tagId: String,
        @Validated @RequestBody request: CreateTagRequest
    ): ResponseEntity<String> {
        log.debug(
            "Creating tag with data: {}",
            sanitizeAndJoinPathVariables(tagId, request.toString()),
        )
        dispatch(
            CreateTagCommand(
                tagId,
                request.name,
                request.color ?: TagColor.DEFAULT.value,
            ),
        )

        return ResponseEntity.created(
            URI.create(
                "/api${
                    TAG.replace(
                        "{tagId}",
                        tagId,
                    )
                }",
            ),
        ).build()
    }

    companion object {
        private val log = LoggerFactory.getLogger(CreateTagController::class.java)
    }
}
