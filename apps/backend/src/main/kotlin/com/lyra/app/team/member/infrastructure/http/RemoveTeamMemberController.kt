package com.lyra.app.team.member.infrastructure.http

import com.lyra.app.team.member.application.remove.RemoveTeamMemberCommand
import com.lyra.common.domain.bus.Mediator
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * Controller class responsible for handling requests to remove a team member.
 * @property mediator The mediator used to dispatch commands.
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class RemoveTeamMemberController(
    private val mediator: Mediator,
) : ApiController(mediator) {

    /**
     * Removes a team member from a team.
     * @param teamId The unique identifier of the team.
     * @param userId The unique identifier of the user.
     */
    @Operation(summary = "Remove a team member")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Team member removed successfully"),
        ApiResponse(responseCode = "404", description = "Team member not found"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @DeleteMapping("/team/{teamId}/member/{userId}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun remove(@PathVariable teamId: String, @PathVariable userId: String) {
        val safeTeamId = sanitizePathVariable(teamId)
        val safeUserId = sanitizePathVariable(userId)
        log.debug("Removing team member with teamId: {} and userId: {}", safeTeamId, safeUserId)
        dispatch(
            RemoveTeamMemberCommand(
                safeTeamId,
                safeUserId,
            ),
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(RemoveTeamMemberController::class.java)
    }
}
