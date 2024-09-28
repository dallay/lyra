package com.lyra.app.newsletter.tag.infrastructure.http

import com.lyra.app.AppConstants.Paths.API
import com.lyra.app.AppConstants.Paths.TAG
import com.lyra.app.newsletter.tag.application.TagResponse
import com.lyra.app.newsletter.tag.application.list.GetAllTagsQuery
import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.presentation.PageResponse
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller for handling requests to get all tags for a specific organization.
 *
 * @property mediator The mediator to handle queries.
 */
@RestController
@RequestMapping(value = [API], produces = ["application/vnd.api.v1+json"])
class GetAllTagController(
    mediator: Mediator,
) : ApiController(mediator) {

    /**
     * Endpoint to get all tags for a specific organization.
     *
     * @param organizationId The ID of the organization to get tags for.
     * @return A ResponseEntity containing a PageResponse with the list of tags.
     */
    @Operation(summary = "Get all tags for a specific organization")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "List of tags"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @GetMapping(TAG)
    suspend fun getAllTags(
        @PathVariable organizationId: String
    ): ResponseEntity<PageResponse<TagResponse>> {
        log.debug("Getting all tags for organization with id: {}", sanitizePathVariable(organizationId))
        val query = GetAllTagsQuery(organizationId)
        val response = ask(query)
        return ResponseEntity.ok(response)
    }

    companion object {
        private val log = LoggerFactory.getLogger(GetAllTagController::class.java)
    }
}
