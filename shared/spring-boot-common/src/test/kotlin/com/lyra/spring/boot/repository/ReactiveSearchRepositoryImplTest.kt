package com.lyra.spring.boot.repository

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageRequest
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

    data class MyEntity(val fieldName: String)
}
