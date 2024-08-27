package com.lyra.app.forms.infrastructure.http

import com.fasterxml.jackson.databind.ObjectMapper
import com.lyra.ControllerTest
import com.lyra.UnitTest
import com.lyra.app.forms.FormStub
import com.lyra.app.forms.application.FormResponse
import com.lyra.app.forms.application.search.SearchFormsQuery
import com.lyra.app.forms.infrastructure.persistence.entity.FormEntity
import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.filter.RHSFilterParser
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.spring.boot.presentation.filter.RHSFilterParserFactory
import com.lyra.spring.boot.presentation.sort.SortParser
import com.lyra.spring.boot.presentation.sort.SortParserFactory
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlin.reflect.KProperty1
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient

private const val NUM_FORMS = 10

@UnitTest
internal class SearchFormControllerTest : ControllerTest() {
    private val organizationId = "27172d5a-b88e-451c-9787-312706f4570d"
    private val rhsFilterParserFactory = mockk<RHSFilterParserFactory>()
    private val rhsFilterParser = mockk<RHSFilterParser<FormEntity>>()
    private val response = FormStub.dummyRandomFormPageResponse(NUM_FORMS)
    private lateinit var controller: SearchFormController
    override lateinit var webTestClient: WebTestClient

    @BeforeEach
    override fun setUp() {
        super.setUp()
        every { rhsFilterParserFactory.create(FormEntity::class) } returns rhsFilterParser
        every { rhsFilterParser.parse(any()) } returns Criteria.Empty
        every { rhsFilterParser.parse(any(), any()) } returns Criteria.Empty

        val sortParserFactory = mockk<SortParserFactory>()
        val sortParser = SortParser(FormEntity::class, ObjectMapper())
        every { sortParserFactory.create(FormEntity::class) } returns sortParser

        coEvery { mediator.send(any(SearchFormsQuery::class)) } returns response
        controller = SearchFormController(mediator, rhsFilterParserFactory, sortParserFactory)
        webTestClient = buildWebTestClient(controller)
    }

    @Test
    fun `should get all forms`() {
        val data: Collection<FormResponse> = response.data
        webTestClient.get().uri("/api/organization/$organizationId/form")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.data.length()").isEqualTo(NUM_FORMS)
            .jsonPath("$.nextPageCursor").isEqualTo(response.nextPageCursor ?: "")
            .jsonPath("$.data[0].id").isEqualTo(data.first().id)
            .jsonPath("$.data[0].name").isEqualTo(data.first().name)
            .jsonPath("$.data[0].description").isEqualTo(data.first().description)
            .jsonPath("$.data[0].inputPlaceholder").isEqualTo(data.first().inputPlaceholder)
            .jsonPath("$.data[0].buttonText").isEqualTo(data.first().buttonText)
            .jsonPath("$.data[0].buttonColor").isEqualTo(data.first().buttonColor)
            .jsonPath("$.data[0].backgroundColor").isEqualTo(data.first().backgroundColor)
            .jsonPath("$.data[0].textColor").isEqualTo(data.first().textColor)
            .jsonPath("$.data[0].buttonTextColor").isEqualTo(data.first().buttonTextColor)
            .jsonPath("$.data[0].createdAt").exists()
            .jsonPath("$.data[0].updatedAt").exists()

        coEvery { mediator.send(any(SearchFormsQuery::class)) }
    }

    @Test
    fun `should get all forms with filter`() {
        val filter = mapOf<KProperty1<FormEntity, *>, List<String>>(
            FormEntity::name to listOf("eq:Newsletter"),
            FormEntity::header to listOf("eq:Subscribe"),
            FormEntity::description to listOf("eq:Subscribe to our newsletter"),
            FormEntity::inputPlaceholder to listOf("eq:Email"),
        )
        val criteria = Criteria.And(
            listOf(
                Criteria.Equals("name", "Newsletter"),
                Criteria.Equals("header", "Subscribe"),
                Criteria.Equals("description", "Subscribe to our newsletter"),
                Criteria.Equals("inputPlaceholder", "Email"),
            ),
        )
        val query = SearchFormsQuery(criteria, 10, null, null)
        coEvery { mediator.send(query) } returns response
        every { rhsFilterParserFactory.create(FormEntity::class) } returns rhsFilterParser
        every { rhsFilterParser.parse(filter) } returns criteria
        val firstElement = response.data.first()
        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/api/organization/$organizationId/form")
                    .queryParam("filter[name]", filter[FormEntity::name])
                    .queryParam("filter[header]", filter[FormEntity::header])
                    .queryParam("filter[description]", filter[FormEntity::description])
                    .queryParam("filter[inputPlaceholder]", filter[FormEntity::inputPlaceholder])
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.data.length()").isEqualTo(NUM_FORMS)
            .jsonPath("$.nextPageCursor").isEqualTo(response.nextPageCursor ?: "")
            .jsonPath("$.data[0].id").isEqualTo(firstElement.id)
            .jsonPath("$.data[0].name").isEqualTo(firstElement.name)
            .jsonPath("$.data[0].description").isEqualTo(firstElement.description)
            .jsonPath("$.data[0].inputPlaceholder").isEqualTo(firstElement.inputPlaceholder)
            .jsonPath("$.data[0].buttonText").isEqualTo(firstElement.buttonText)
            .jsonPath("$.data[0].buttonColor").isEqualTo(firstElement.buttonColor)
            .jsonPath("$.data[0].backgroundColor").isEqualTo(firstElement.backgroundColor)
            .jsonPath("$.data[0].textColor").isEqualTo(firstElement.textColor)
            .jsonPath("$.data[0].buttonTextColor").isEqualTo(firstElement.buttonTextColor)
            .jsonPath("$.data[0].createdAt").exists()
            .jsonPath("$.data[0].updatedAt").exists()

        coEvery { mediator.send(query) }
    }

    @Test
    fun `should get all forms by search`() {
        val search = "Subscribe"
        val criteria = Criteria.Or(
            listOf(
                Criteria.Like("name", search),
                Criteria.Like("header", search),
                Criteria.Like("description", search),
                Criteria.Like("inputPlaceholder", search),
                Criteria.Like("buttonText", search),
            ),
        )
        val query = SearchFormsQuery(criteria)
        coEvery { mediator.send(query) } returns response
        every { rhsFilterParserFactory.create(FormEntity::class) } returns rhsFilterParser
        every { rhsFilterParser.parse(any(), eq(true)) } returns criteria
        val firstElement = response.data.first()
        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/api/organization/$organizationId/form")
                    .queryParam("search", search)
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.data.length()").isEqualTo(NUM_FORMS)
            .jsonPath("$.nextPageCursor").isEqualTo(response.nextPageCursor ?: "")
            .jsonPath("$.data[0].id").isEqualTo(firstElement.id)
            .jsonPath("$.data[0].name").isEqualTo(firstElement.name)
            .jsonPath("$.data[0].description").isEqualTo(firstElement.description)
            .jsonPath("$.data[0].inputPlaceholder").isEqualTo(firstElement.inputPlaceholder)
            .jsonPath("$.data[0].buttonText").isEqualTo(firstElement.buttonText)
            .jsonPath("$.data[0].buttonColor").isEqualTo(firstElement.buttonColor)
            .jsonPath("$.data[0].backgroundColor").isEqualTo(firstElement.backgroundColor)
            .jsonPath("$.data[0].textColor").isEqualTo(firstElement.textColor)
            .jsonPath("$.data[0].buttonTextColor").isEqualTo(firstElement.buttonTextColor)
            .jsonPath("$.data[0].createdAt").exists()
            .jsonPath("$.data[0].updatedAt").exists()

        coEvery { mediator.send(query) }
    }

    @Test
    fun `should combine search and filters`() {
        val search = "search value"
        val filter = mapOf<KProperty1<FormEntity, *>, List<String>>(
            FormEntity::name to listOf("eq:Newsletter"),
            FormEntity::header to listOf("eq:Subscribe"),
            FormEntity::description to listOf("eq:Subscribe to our newsletter"),
            FormEntity::inputPlaceholder to listOf("eq:Email"),
        )
        val criteria = Criteria.Or(
            listOf(
                Criteria.Like("name", search),
                Criteria.Like("header", search),
                Criteria.Like("description", search),
                Criteria.Like("inputPlaceholder", search),
                Criteria.Like("buttonText", search),
                Criteria.Equals("name", "Newsletter"),
                Criteria.Equals("header", "Subscribe"),
                Criteria.Equals("description", "Subscribe to our newsletter"),
                Criteria.Equals("inputPlaceholder", "Email"),
            ),
        )
        val query = SearchFormsQuery(criteria)
        coEvery { mediator.send(query) } returns response
        every { rhsFilterParserFactory.create(FormEntity::class) } returns rhsFilterParser
        every { rhsFilterParser.parse(any(), eq(true)) } returns criteria
        val firstElement = response.data.first()
        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/api/organization/$organizationId/form")
                    .queryParam("search", search)
                    .queryParam("filter[name]", filter[FormEntity::name])
                    .queryParam("filter[header]", filter[FormEntity::header])
                    .queryParam("filter[description]", filter[FormEntity::description])
                    .queryParam("filter[inputPlaceholder]", filter[FormEntity::inputPlaceholder])
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.data.length()").isEqualTo(NUM_FORMS)
            .jsonPath("$.nextPageCursor").isEqualTo(response.nextPageCursor ?: "")
            .jsonPath("$.data[0].id").isEqualTo(firstElement.id)
            .jsonPath("$.data[0].name").isEqualTo(firstElement.name)
            .jsonPath("$.data[0].description").isEqualTo(firstElement.description)
            .jsonPath("$.data[0].inputPlaceholder").isEqualTo(firstElement.inputPlaceholder)
            .jsonPath("$.data[0].buttonText").isEqualTo(firstElement.buttonText)
            .jsonPath("$.data[0].buttonColor").isEqualTo(firstElement.buttonColor)
            .jsonPath("$.data[0].backgroundColor").isEqualTo(firstElement.backgroundColor)
            .jsonPath("$.data[0].textColor").isEqualTo(firstElement.textColor)
            .jsonPath("$.data[0].buttonTextColor").isEqualTo(firstElement.buttonTextColor)
            .jsonPath("$.data[0].createdAt").exists()
            .jsonPath("$.data[0].updatedAt").exists()

        coEvery { mediator.send(query) }
    }

    @Test
    fun `should get all forms with sort`() {
        val sort = listOf("asc:name", "desc:createdAt")
        val firstElement = response.data.first()
        webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/api/organization/$organizationId/form")
                    .queryParam("sort", sort)
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data").isArray
            .jsonPath("$.nextPageCursor").isEqualTo(response.nextPageCursor ?: "")
            .jsonPath("$.data[0].id").isEqualTo(firstElement.id)
            .jsonPath("$.data[0].name").isEqualTo(firstElement.name)
            .jsonPath("$.data[0].description").isEqualTo(firstElement.description)
            .jsonPath("$.data[0].inputPlaceholder").isEqualTo(firstElement.inputPlaceholder)
            .jsonPath("$.data[0].buttonText").isEqualTo(firstElement.buttonText)
            .jsonPath("$.data[0].buttonColor").isEqualTo(firstElement.buttonColor)
            .jsonPath("$.data[0].backgroundColor").isEqualTo(firstElement.backgroundColor)
            .jsonPath("$.data[0].textColor").isEqualTo(firstElement.textColor)
            .jsonPath("$.data[0].buttonTextColor").isEqualTo(firstElement.buttonTextColor)
            .jsonPath("$.data[0].createdAt").exists()
            .jsonPath("$.data[0].updatedAt").exists()
    }

    @Test
    fun `should paginate forms using nextCursor from response`() {
        val pageSize = 5
        val firstResponse = FormStub.dummyRandomFormPageResponse(pageSize)
        val firstQuery = SearchFormsQuery(size = pageSize)
        coEvery { mediator.send(firstQuery) } returns firstResponse

        val firstRequest = webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/api/organization/$organizationId/form")
                    .queryParam("size", pageSize)
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody(CursorPageResponse::class.java)
            .returnResult()
            .responseBody

        val secondResponse = FormStub.dummyRandomFormPageResponse(pageSize - 2)
        val secondQuery = SearchFormsQuery(size = pageSize, cursor = firstRequest?.nextPageCursor)
        coEvery { mediator.send(secondQuery) } returns secondResponse

        if (firstRequest != null) {
            webTestClient.get()
                .uri { uriBuilder ->
                    uriBuilder
                        .path("/api/organization/$organizationId/form")
                        .queryParam("size", pageSize)
                        .queryParam("cursor", firstRequest.nextPageCursor)
                        .build()
                }
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data").isArray
                .jsonPath("$.data.length()").isEqualTo(secondResponse.data.size)
                .jsonPath("$.nextPageCursor").isEqualTo(secondResponse.nextPageCursor ?: "")
        }
    }
}
