package com.lyra.app.forms.infrastructure.http

import com.lyra.app.forms.application.search.SearchFormsQuery
import com.lyra.app.forms.infrastructure.persistence.entity.FormEntity
import com.lyra.common.domain.bus.Mediator
import com.lyra.common.domain.bus.query.Response
import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.pagination.CursorRequestPageable
import com.lyra.spring.boot.ApiController
import com.lyra.spring.boot.presentation.filter.RHSFilterParserFactory
import com.lyra.spring.boot.presentation.sort.SortParserFactory
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.util.*
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * This is a REST controller for searching forms.
 * It extends the ApiController class and uses the Mediator design pattern for handling requests.
 * It uses the RHSFilterParserFactory and SortParserFactory for parsing filters and sorting parameters.
 */
@RestController
@RequestMapping(value = ["/api"], produces = ["application/vnd.api.v1+json"])
class SearchFormController(
    mediator: Mediator,
    rhsFilterParserFactory: RHSFilterParserFactory,
    sortParserFactory: SortParserFactory,
) : ApiController(mediator) {
    private val rhsFilterParser = rhsFilterParserFactory.create(FormEntity::class)
    private val sortParser = sortParserFactory.create(FormEntity::class)

    @Operation(summary = "Search forms")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Forms found"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "500", description = "Internal server error"),
    )
    @GetMapping("/$ENDPOINT_FORM")
    suspend fun search(cursorRequestPageable: CursorRequestPageable): Response {
        log.debug("Searching forms with cursor: {}", cursorRequestPageable)
        val criteria: Criteria = criteria(cursorRequestPageable)

        val response = ask(
            SearchFormsQuery(
                criteria,
                cursorRequestPageable.size,
                cursorRequestPageable.cursor,
                cursorRequestPageable.sort?.let { if (it.isEmpty()) null else sortParser.parse(it) },
            ),
        )
        return response
    }

    /**
     * Creates a criteria object from the cursor request pageable.
     * The criteria object is used to filter the results. It is created from the search and filter
     * properties of the cursor request pageable.
     * Search is used to filter the email, firstname and lastname fields.
     * Filter is used to filter the fields of the entity.
     *
     * @param cursorRequestPageable The cursor request pageable.
     * @return The criteria object.
     */
    private fun criteria(cursorRequestPageable: CursorRequestPageable): Criteria {
        val searchMap = mutableMapOf<KProperty1<FormEntity, *>, List<String>>()
        val filterMap = mutableMapOf<KProperty1<FormEntity, *>, List<String>>()
        val search = cursorRequestPageable.search
        val searchQuery = if (!search.isNullOrBlank()) getSearchQuery(search) else emptyList()
        val searchableProperties = mapOf(
            FormEntity::name to searchQuery,
            FormEntity::header to searchQuery,
            FormEntity::description to searchQuery,
            FormEntity::inputPlaceholder to searchQuery,
            FormEntity::buttonText to searchQuery,
        )
        FormEntity::class.memberProperties.forEach { property ->
            val propertySearch = searchableProperties.getOrDefault(property, emptyList())
            val propertyFilter = cursorRequestPageable.filter.getOrDefault(property.name, emptyList())
            searchMap[property] = propertySearch
            filterMap[property] = propertyFilter
        }
        val searchCriteria = rhsFilterParser.parse(searchMap, useOr = true)
        val filterCriteria = rhsFilterParser.parse(filterMap, useOr = false)

        val criteriaList = mutableListOf<Criteria>()
        if (filterCriteria !is Criteria.Empty) {
            criteriaList.add(filterCriteria)
        }
        if (searchCriteria !is Criteria.Empty) {
            criteriaList.add(searchCriteria)
        }

        val criteria = if (criteriaList.isNotEmpty()) Criteria.And(criteriaList) else Criteria.Empty
        return criteria
    }

    /**
     * Creates a list of search queries from the given search string.
     * The search query is used to filter the email, firstname and lastname fields.
     * The search query is created from the given search string and its lowercase, uppercase and titlecase
     * versions.
     *
     * @param search The search string.
     * @return The list of search queries.
     */
    private fun getSearchQuery(search: String): List<String> {
        val trimQuery = search.trim()
        val searchQuery = listOf(
            "lk:$trimQuery",
            "lk:${trimQuery.lowercase()}",
            "lk:${trimQuery.uppercase()}",
            "lk:${
                trimQuery.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }
            }",
        )
        return searchQuery
    }

    companion object {
        private val log = LoggerFactory.getLogger(SearchFormController::class.java)
    }
}
