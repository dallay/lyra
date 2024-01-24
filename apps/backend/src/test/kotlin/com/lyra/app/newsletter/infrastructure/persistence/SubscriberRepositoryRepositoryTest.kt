package com.lyra.app.newsletter.infrastructure.persistence

import com.lyra.app.UnitTest
import com.lyra.app.newsletter.SubscriberStub
import com.lyra.app.newsletter.domain.Subscriber
import com.lyra.app.newsletter.infrastructure.persistence.entity.SubscriberEntity
import com.lyra.app.newsletter.infrastructure.persistence.mapper.SubscriberMapper.toEntity
import com.lyra.app.newsletter.infrastructure.persistence.repository.SubscriberRegistratorR2dbcRepository
import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.sort.Sort
import com.lyra.spring.boot.presentation.sort.toSpringSort
import com.lyra.spring.boot.repository.R2DBCCriteriaParser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@UnitTest
internal class SubscriberRepositoryRepositoryTest {
    private val subscriberRegistratorR2dbcRepository: SubscriberRegistratorR2dbcRepository = mockk()
    private val subscriberRepositoryRepository =
        SubscriberRepositoryRepository(subscriberRegistratorR2dbcRepository)
    private lateinit var subscribers: List<Subscriber>

    @BeforeEach
    fun setUp() {
        subscribers = runBlocking { SubscriberStub.dummyRandomSubscribersFlow(10).toList() }
        val subscribersEntities = subscribers.map { it.toEntity() }.toList()
        coEvery { subscriberRegistratorR2dbcRepository.save(any()) } returns Mono.just(
            subscribersEntities.first(),
        )
        coEvery { subscriberRegistratorR2dbcRepository.findAll() } returns Flux.fromIterable(
            subscribersEntities,
        )
        coEvery {
            subscriberRegistratorR2dbcRepository.findAll(
                any(org.springframework.data.relational.core.query.Criteria::class),
                any(Pageable::class),
                eq(SubscriberEntity::class),
            )
        } returns Mono.just(
            org.springframework.data.domain.PageImpl(
                subscribersEntities,
            ),
        )
        coEvery { subscriberRegistratorR2dbcRepository.findAllByStatus(any()) } returns Flux.fromIterable(
            subscribersEntities,
        )
    }

    @Test
    fun `should save a new subscriber`() {

        val subscriber = subscribers.first()

        runBlocking {
            subscriberRepositoryRepository.create(subscriber)
        }

        coEvery { subscriberRegistratorR2dbcRepository.save(any()) }
    }

    @Test
    fun `should not save a new subscriber if it already exists`() {

        val subscriber = subscribers.first()
        coEvery { subscriberRegistratorR2dbcRepository.save(any()) } returns Mono.error(
            DuplicateKeyException("Duplicate key"),
        )

        runBlocking {
            subscriberRepositoryRepository.create(subscriber)
        }

        coEvery { subscriberRegistratorR2dbcRepository.save(any()) }
    }

    @Test
    fun `should not save a new subscriber if an unknown exception occur`() {

        val subscriber = subscribers.first()
        coEvery { subscriberRegistratorR2dbcRepository.save(any()) } returns Mono.error(
            Exception("Unknown exception"),
        )

        runBlocking {
            subscriberRepositoryRepository.create(subscriber)
        }

        coEvery { subscriberRegistratorR2dbcRepository.save(any()) }
    }

    @Test
    fun `should search all subscribers`() = runBlocking {

        val response = subscriberRepositoryRepository.searchAll(Criteria.Empty)
        assertEquals(subscribers, response.data)
        coVerify(exactly = 1) {
            subscriberRegistratorR2dbcRepository.findAll(
                any(org.springframework.data.relational.core.query.Criteria::class),
                any(Pageable::class),
                eq(SubscriberEntity::class),
            )
        }
    }

    @Test
    fun `should search all active subscribers`() = runBlocking {

        val response = subscriberRepositoryRepository.searchActive().toList()
        assertEquals(subscribers, response)
    }

    @Test
    fun `should search all BLOCKLISTED subscribers by criteria`() = runBlocking {

        val criteria = Criteria.Equals("status", "BLOCKLISTED")

        val response = subscriberRepositoryRepository.searchAll(criteria)
        assertEquals(subscribers, response.data)
        coVerify(exactly = 1) {
            subscriberRegistratorR2dbcRepository.findAll(
                any(org.springframework.data.relational.core.query.Criteria::class),
                any(Pageable::class),
                eq(SubscriberEntity::class),
            )
        }
    }

    @Test
    fun `should search all DISABLED subscribers by criteria`() = runBlocking {

        val criteria = Criteria.Equals("status", "DISABLED")

        val response = subscriberRepositoryRepository.searchAll(criteria)
        assertEquals(subscribers, response.data)
        coVerify(exactly = 1) {
            subscriberRegistratorR2dbcRepository.findAll(
                any(org.springframework.data.relational.core.query.Criteria::class),
                any(Pageable::class),
                eq(SubscriberEntity::class),
            )
        }
    }

    @Test
    fun `should search by criteria with multiple filters`() = runBlocking {

        val criteria = Criteria.And(
            listOf(
                Criteria.Equals("email", "email"),
                Criteria.Equals("firstname", "firstname"),
                Criteria.Equals("lastname", "lastname"),
                Criteria.Equals("status", "status"),
            ),
        )

        val response = subscriberRepositoryRepository.searchAll(criteria)
        assertEquals(subscribers, response.data)
        coVerify(exactly = 1) {
            subscriberRegistratorR2dbcRepository.findAll(
                any(org.springframework.data.relational.core.query.Criteria::class),
                any(Pageable::class),
                eq(SubscriberEntity::class),
            )
        }
    }

    @Test
    fun `should search by criteria with multiple filters and sort`() = runBlocking {

        val criteria = Criteria.And(
            listOf(
                Criteria.Equals("email", "email"),
                Criteria.Equals("firstname", "firstname"),
                Criteria.Equals("lastname", "lastname"),
                Criteria.Equals("status", "status"),
            ),
        )
        val sort = Sort.by("email", "ASC").and(Sort.by("firstname", "DESC"))
        val criteriaParsed = R2DBCCriteriaParser(SubscriberEntity::class).parse(criteria)
        val sortCriteria = sort.toSpringSort()
        val pageable = PageRequest.of(0, 10, sortCriteria)
        coEvery {
            subscriberRegistratorR2dbcRepository.findAll(
                eq(criteriaParsed),
                eq(pageable),
                eq(SubscriberEntity::class),
            )
        } returns Mono.just(
            PageImpl(
                subscribers.map { it.toEntity() },
            ),
        )

        val response = subscriberRepositoryRepository.searchAll(criteria, 10, 0, sort)
        assertEquals(subscribers, response.data)
    }
}
