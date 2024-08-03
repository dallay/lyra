package com.lyra.app.team.infrastructure.http

import com.lyra.app.team.application.CreateTeamCommand
import com.lyra.app.team.infrastructure.http.request.CreateTeamRequest
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
 * Controller class responsible for handling requests to create a team.
 * @property mediator The mediator used to dispatch commands.
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class CreateTeamController(
    private val mediator: Mediator,
) : ApiController(mediator) {

    /**
     * Adds a team member to a team.
     * @param id The ID of the team.
     * @param request The request containing the information needed to add a team member.
     */
    @Operation(summary = "Create a team with the given data")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Created"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @PutMapping("/$ENDPOINT_TEAM/{id}")
    suspend fun create(
        @PathVariable id: String,
        @Validated @RequestBody request: CreateTeamRequest,
    ): ResponseEntity<String> {
        val safeId = sanitizePathVariable(id)
        log.debug("Creating Team with ID: {}", safeId)
        dispatch(
            CreateTeamCommand(
                safeId,
                request.organizationId,
                request.name,
            ),
        )
        return ResponseEntity.created(URI.create("/api/$ENDPOINT_TEAM")).build()
    }

    companion object {
        private val log = LoggerFactory.getLogger(CreateTeamController::class.java)
    }
}
