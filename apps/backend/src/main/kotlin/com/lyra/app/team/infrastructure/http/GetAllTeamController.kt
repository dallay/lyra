package com.lyra.app.team.infrastructure.http

import com.lyra.app.organization.infrastructure.http.ENDPOINT_ORGANIZATION
import com.lyra.app.team.application.find.all.AllTeamQuery
import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.query.Response
import com.lyra.spring.boot.ApiController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class GetAllTeamController(
    mediator: Mediator
) : ApiController(mediator) {

    @Operation(summary = "Get all teams that belong to the organization")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Success"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @GetMapping("/$ENDPOINT_ORGANIZATION/{organizationId}/$ENDPOINT_TEAM")
    @ResponseBody
    suspend fun findAll(@PathVariable("organizationId") organizationId: String): Response {
        log.debug("Get All teams for organization with id: $organizationId")

        val response = ask(
            AllTeamQuery(organizationId),
        )
        return response
    }

    companion object {
        private val log = LoggerFactory.getLogger(GetAllTeamController::class.java)
    }
}
