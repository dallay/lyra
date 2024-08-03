package com.lyra.app.team.member.infrastructure.http

import com.lyra.app.team.member.application.AddTeamMemberCommand
import com.lyra.app.team.member.infrastructure.http.request.AddTeamMemberRequest
import com.lyra.common.domain.bus.Mediator
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.net.URI
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller class responsible for handling requests to add a team member.
 * @property mediator The mediator used to dispatch commands.
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class AddTeamMemberController(
    private val mediator: Mediator,
) : ApiController(mediator) {
    /**
     * Adds a team member to a team.
     * @param request The request containing the information needed to add a team member.
     */
    @Operation(summary = "Add team member with the given data")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Created"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @PutMapping("/$ENDPOINT_TEAM_MEMBER")
    suspend fun create(
        @Validated @RequestBody request: AddTeamMemberRequest
    ): ResponseEntity<String> {
        val teamId = sanitizePathVariable(request.teamId)
        val userId = sanitizePathVariable(request.userId)
        val role = sanitizePathVariable(request.role)
        log.debug(
            "Add team member with teamId: {} and userId: {}",
            teamId, userId,
        )
        dispatch(
            AddTeamMemberCommand(
                teamId,
                userId,
                role,
            ),
        )
        return ResponseEntity.created(URI.create("/api/$ENDPOINT_TEAM_MEMBER")).build()
    }
    companion object {
        private val log = LoggerFactory.getLogger(AddTeamMemberController::class.java)
    }
}
