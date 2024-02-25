package com.lyra.app.newsletter.infrastructure.http

import com.lyra.app.newsletter.application.search.all.SearchAllSubscribersQuery
import com.lyra.app.newsletter.infrastructure.persistence.entity.SubscriberEntity
import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.query.Response
import com.lyra.common.domain.presentation.pagination.CursorRequestPageable
import com.lyra.spring.boot.ApiController
import com.lyra.spring.boot.presentation.filter.RHSFilterParserFactory
import com.lyra.spring.boot.presentation.sort.SortParserFactory
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import kotlin.reflect.full.memberProperties
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
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
    suspend fun findAll(cursorRequestPageable: CursorRequestPageable): Response {
        log.debug("Get all subscribers with cursor: {}", cursorRequestPageable)

        val searchMap = mutableMapOf<kotlin.reflect.KProperty1<SubscriberEntity, *>, List<String>>()
        SubscriberEntity::class.memberProperties.forEach { property ->
            searchMap[property] = cursorRequestPageable.search.getOrDefault(property.name, emptyList())
        }

        val criteria = rhsFilterParser.parse(searchMap)

        val response = ask(
            SearchAllSubscribersQuery(
                criteria,
                cursorRequestPageable.size,
                cursorRequestPageable.cursor,
                cursorRequestPageable.sort?.let { if (it.isEmpty()) null else sortParser.parse(it) },
            ),
        )
        return response
    }

    companion object {
        private val log = LoggerFactory.getLogger(GetAllSubscriberController::class.java)
    }
}
