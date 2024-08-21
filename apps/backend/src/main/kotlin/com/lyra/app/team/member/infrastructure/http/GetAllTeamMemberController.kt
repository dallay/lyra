package com.lyra.app.team.member.infrastructure.http

import com.lyra.app.team.member.application.all.GetAllTeamMember
import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.query.QueryResponse
import com.lyra.common.domain.bus.query.Response
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller for handling requests to get all team members.
 *
 * @property mediator The mediator used to send queries.
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class GetAllTeamMemberController(
    mediator: Mediator
) : ApiController(mediator) {

    /**
     * Endpoint to get all members that belong to the team and organization.
     *
     * @return A response containing the list of team members.
     */
    @Operation(summary = "Get all members that belong to the team and organization")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Success"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @GetMapping("/team/member")
    suspend fun findAll(): ResponseEntity<Response> {
        val authentication = authentication()
        val jwt = authentication?.principal as? Jwt
        val userId = jwt?.claims?.get("sub") as? String
        log.debug("Get All team members for user: {}", userId)
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(QueryResponse("User not found"))
        }
        val response = ask(
            GetAllTeamMember(userId),
        )
        return ResponseEntity.ok(response)
    }

    companion object {
        private val log = LoggerFactory.getLogger(GetAllTeamMemberController::class.java)
    }
}
