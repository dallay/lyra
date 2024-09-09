package com.lyra.app.newsletter.infrastructure.http

import com.lyra.app.AppConstants.Paths.API
import com.lyra.app.AppConstants.Paths.SUBSCRIBER
import com.lyra.app.newsletter.application.count.bystatus.CountByStatusQuery
import com.lyra.app.newsletter.application.count.bystatus.SubscriberCountByStatusResponse
import com.lyra.common.domain.bus.Mediator
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
@RequestMapping(value = [API], produces = ["application/vnd.api.v1+json"])
class SubscriberCountByStatusController(
    mediator: Mediator
) : ApiController(mediator) {

    @Operation(summary = "Get all subscribers")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Success"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @GetMapping("$SUBSCRIBER/count-by-status")
    @ResponseBody
    suspend fun countByStatus(
        @PathVariable organizationId: String
    ): SubscriberCountByStatusResponse {
        log.debug("Counting subscribers by status: {}", sanitizeAndJoinPathVariables(organizationId))
        val response = ask(
            CountByStatusQuery(),
        )
        return response as SubscriberCountByStatusResponse
    }

    companion object {
        private val log = LoggerFactory.getLogger(SubscriberCountByStatusController::class.java)
    }
}
