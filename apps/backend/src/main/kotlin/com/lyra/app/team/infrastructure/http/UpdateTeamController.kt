package com.lyra.app.team.infrastructure.http

import com.lyra.app.team.application.update.UpdateTeamCommand
import com.lyra.app.team.infrastructure.http.request.UpdateTeamRequest
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
 * This class is a REST controller for updating teams.
 * It extends the ApiController class and uses the Mediator pattern for handling requests.
 *
 * @property mediator The mediator used for handling requests.
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class UpdateTeamController(
    private val mediator: Mediator,
) : ApiController(mediator) {

    /**
     * This method handles the PUT request for updating a team.
     * It validates the request body and dispatches an UpdateTeamCommand.
     * @param id The ID of the team to update.
     * @param request The request body containing the new team data.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @Operation(summary = "Update a team with the given data")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Update a team"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @PutMapping("/$ENDPOINT_TEAM/update/{id}")
    suspend fun create(
        @PathVariable id: String,
        @Validated @RequestBody request: UpdateTeamRequest,
    ): ResponseEntity<String> {
        log.debug("Update Team with ID: {}", id)
        dispatch(
            UpdateTeamCommand(
                id,
                request.name,
            ),
        )
        return ResponseEntity.ok("Team updated successfully")
    }

    companion object {
        private val log = LoggerFactory.getLogger(UpdateTeamController::class.java)
    }
}
