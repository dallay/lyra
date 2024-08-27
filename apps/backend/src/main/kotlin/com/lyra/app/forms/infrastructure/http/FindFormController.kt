package com.lyra.app.forms.infrastructure.http

import com.lyra.app.AppConstants.Paths.API
import com.lyra.app.AppConstants.Paths.FORMS_ID_V1
import com.lyra.app.forms.application.find.FindFormQuery
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
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [API], produces = ["application/vnd.api.v1+json"])
class FindFormController(
    mediator: Mediator,
) : ApiController(mediator) {
    @Operation(summary = "Find a form by ID")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Found form"),
        ApiResponse(responseCode = "404", description = "Form not found"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @GetMapping(FORMS_ID_V1)
    suspend fun find(@PathVariable organizationId: String, @PathVariable formId: String): Response {
        log.debug("Finding form with ids: {}", sanitizeAndJoinPathVariables(organizationId, formId))
        val query = FindFormQuery(organizationId = organizationId, formId = formId)
        val response = ask(query)
        return response
    }

    companion object {
        private val log = LoggerFactory.getLogger(FindFormController::class.java)
    }
}
