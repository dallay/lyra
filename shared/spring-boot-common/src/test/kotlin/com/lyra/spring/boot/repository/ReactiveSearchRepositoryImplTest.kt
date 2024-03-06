package com.lyra.spring.boot.repository

import com.lyra.common.domain.presentation.pagination.TimestampCursor
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime
import kotlin.random.Random
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import reactor.core.publisher.Flux.fromIterable
import reactor.core.publisher.Mono.just
import reactor.test.StepVerifier

class ReactiveSearchRepositoryImplTest {

    @Test
    fun testFindAll() {
        val r2dbcTemplate = mockk<R2dbcEntityTemplate>()
        val repository = ReactiveSearchRepositoryImpl<MyEntity>(r2dbcTemplate)

        val criteria = Criteria.where("fieldName").`is`("fieldValue")
        val domainType = MyEntity::class

        val mockEntityList = listOf(MyEntity("fieldValue"))
        every {
            r2dbcTemplate.select(MyEntity::class.java)
                .matching(any())
                .all()
        } returns fromIterable(mockEntityList)

        val resultFlux = repository.findAll(criteria, domainType)

        StepVerifier.create(resultFlux)
            .expectNextSequence(mockEntityList)
            .verifyComplete()
    }

    @Test
    fun testFindAllWithPageable() {
        val r2dbcTemplate = mockk<R2dbcEntityTemplate>()
        val repository = ReactiveSearchRepositoryImpl<MyEntity>(r2dbcTemplate)

        val criteria = Criteria.where("fieldName").`is`("fieldValue")
        val pageable = PageRequest.of(0, 10)
        val domainType = MyEntity::class

        val mockEntityList = listOf(MyEntity("fieldValue"))
        every {
            r2dbcTemplate.select(MyEntity::class.java)
                .matching(any())
                .all()
                .collectList()
        } returns just(mockEntityList)
        every {
            r2dbcTemplate.select(MyEntity::class.java)
                .matching(any())
                .count()
        } returns just(1L)

        val resultMono = repository.findAll(criteria, pageable, domainType)

        StepVerifier.create(resultMono)
            .expectNextMatches { page ->
                page.content == mockEntityList && page.totalElements == 1L && page.pageable == pageable
            }
            .verifyComplete()
    }

    @Test
    fun `should search all subscribers by cursor pagination with criteria and sort`() {
        val r2dbcTemplate = mockk<R2dbcEntityTemplate>()
        val repository = ReactiveSearchRepositoryImpl<MyEntity>(r2dbcTemplate)
        val criteria = Criteria.where("id").greaterThan(1)
        val sort = Sort.by("fieldName", "ASC")
        val cursor = TimestampCursor(createdAt = LocalDateTime.parse("2021-01-01T00:00:00"))
        val mockEntityList =
            listOf(
                MyEntity(fieldName = "fieldValue", createdAt = LocalDateTime.parse("2022-01-01T00:00:00")),
                MyEntity(fieldName = "fieldValue2", createdAt = LocalDateTime.parse("2022-03-01T00:00:00")),
                MyEntity(fieldName = "fieldValue3", createdAt = LocalDateTime.parse("2022-04-01T00:00:00")),
            )
        every {
            r2dbcTemplate.select(MyEntity::class.java)
                .matching(any())
                .all()
        } returns fromIterable(mockEntityList)

        val resultFlux = repository.findAllByCursor(criteria, 2, MyEntity::class, sort, cursor)

        StepVerifier.create(resultFlux)
            .consumeNextWith { responsePage ->
                println("Actual data: ${responsePage.data}")
                val subList = mockEntityList.subList(0, 2)
                println("Expected data: $subList")
                println("Actual nextPageCursor: ${responsePage.nextPageCursor}")
                println("Expected nextPageCursor: MjAyMi0wMy0wMVQwMDowMDzigJQ+QVND")
                assertEquals(subList, responsePage.data)
                val expectedCursor = "MjAyMi0wMy0wMVQwMDowMDzigJQ+QVND"
                assertEquals(expectedCursor, responsePage.nextPageCursor)
            }
            .verifyComplete()
    }

    data class MyEntity(
        val fieldName: String,
        val id: Long = Random.nextLong(from = 1000, until = 3000),
        val createdAt: LocalDateTime = LocalDateTime.now()
    )
}
