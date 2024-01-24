package com.lyra.app.newsletter.infrastructure.http

import com.lyra.app.newsletter.application.search.all.SearchAllSubscribersQuery
import com.lyra.app.newsletter.infrastructure.persistence.entity.SubscriberEntity
import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.query.Response
import com.lyra.spring.boot.ApiController
import com.lyra.spring.boot.presentation.filter.RHSFilterParserFactory
import com.lyra.spring.boot.presentation.sort.SortParserFactory
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class GetAllSubscriberController(
    mediator: Mediator,
    rhsFilterParserFactory: RHSFilterParserFactory,
    sortParserFactory: SortParserFactory,
) : ApiController(mediator) {
    private val rhsFilterParser = rhsFilterParserFactory.create(SubscriberEntity::class)
    private val sortParser = sortParserFactory.create(SubscriberEntity::class)

    @Operation(summary = "Get all subscribers")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Success"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @GetMapping("/newsletter/subscribers")
    @ResponseBody
    suspend fun findAll(
        @RequestParam(required = false) email: List<String>? = null,
        @RequestParam(required = false) firstname: List<String>? = null,
        @RequestParam(required = false) lastname: List<String>? = null,
        @RequestParam(required = false) status: List<String>? = null,
        @RequestParam("created_at", required = false) createdAt: List<String>? = null,
        @RequestParam("updated_at", required = false) updatedAt: List<String>? = null,
        @RequestParam("sort", required = false) sort: List<String>? = null,
        @RequestParam("page", required = false) page: Long? = null,
        @RequestParam("per_page", required = false) perPage: Int? = null,
    ): Response {
        log.debug(
            "Get all subscribers with filters: {}, {}, {}, {}, {}, {}",
            email,
            firstname,
            lastname,
            status,
            createdAt,
            updatedAt,
        )
        val criteria = rhsFilterParser.parse(
            mapOf(
                SubscriberEntity::email to email,
                SubscriberEntity::firstname to firstname,
                SubscriberEntity::lastname to lastname,
                SubscriberEntity::status to status,
                SubscriberEntity::createdAt to createdAt,
                SubscriberEntity::updatedAt to updatedAt,
            ),
        )
        val response = ask(
            SearchAllSubscribersQuery(
                criteria,
                perPage,
                page,
                sort?.let { sortParser.parse(it) },
            ),
        )
        return response
    }

    companion object {
        private val log = LoggerFactory.getLogger(GetAllSubscriberController::class.java)
    }
}
