package com.lyra.app.newsletter.infrastructure.persistence

import com.lyra.app.UnitTest
import com.lyra.app.newsletter.SubscriberStub
import com.lyra.app.newsletter.domain.Subscriber
import com.lyra.app.newsletter.infrastructure.persistence.entity.SubscriberEntity
import com.lyra.app.newsletter.infrastructure.persistence.mapper.SubscriberMapper.toEntity
import com.lyra.app.newsletter.infrastructure.persistence.repository.SubscriberRegistratorR2dbcRepository
import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.pagination.Cursor
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.common.domain.presentation.pagination.OffsetPageResponse
import com.lyra.common.domain.presentation.pagination.TimestampCursor
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
internal class SubscriberR2dbcRepositoryTest {
    private val subscriberRegistratorR2dbcRepository: SubscriberRegistratorR2dbcRepository = mockk()
    private val subscriberRepository =
        SubscriberR2dbcRepository(subscriberRegistratorR2dbcRepository)
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
            PageImpl(
                subscribersEntities,
            ),
        )
        coEvery {
            subscriberRegistratorR2dbcRepository.findAllByCursor(
                any(org.springframework.data.relational.core.query.Criteria::class),
                any(Int::class),
                eq(SubscriberEntity::class),
                any(org.springframework.data.domain.Sort::class),
                any(Cursor::class),
            )
        } returns Mono.just(
            CursorPageResponse(
                data = subscribersEntities,
                nextPageCursor = TimestampCursor(
                    subscribersEntities.last().createdAt,
                ).serialize(),
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
            subscriberRepository.create(subscriber)
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
            subscriberRepository.create(subscriber)
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
            subscriberRepository.create(subscriber)
        }

        coEvery { subscriberRegistratorR2dbcRepository.save(any()) }
    }

    @Test
    fun `should search all subscribers by offset pagination`() = runBlocking {

        val response: OffsetPageResponse<Subscriber> = subscriberRepository.searchAllByOffset(Criteria.Empty)
        assertEquals(subscribers, response.data.toList())
        coVerify(exactly = 1) {
            subscriberRegistratorR2dbcRepository.findAll(
                any(org.springframework.data.relational.core.query.Criteria::class),
                any(Pageable::class),
                eq(SubscriberEntity::class),
            )
        }
    }

    @Test
    fun `should search all subscribers by cursor pagination`() = runBlocking {

        val response: CursorPageResponse<Subscriber> = subscriberRepository.searchAllByCursor(Criteria.Empty)
        assertEquals(subscribers, response.data.toList())
        coVerify(exactly = 1) {
            subscriberRegistratorR2dbcRepository.findAllByCursor(
                any(org.springframework.data.relational.core.query.Criteria::class),
                any(Int::class),
                eq(SubscriberEntity::class),
                any(org.springframework.data.domain.Sort::class),
                any(Cursor::class),
            )
        }
    }

    @Test
    fun `should search all active subscribers`() = runBlocking {

        val response = subscriberRepository.searchActive().toList()
        assertEquals(subscribers, response)
    }

    @Test
    fun `should search all BLOCKLISTED subscribers by criteria using offset pagination`() = runBlocking {

        val criteria = Criteria.Equals("status", "BLOCKLISTED")

        val response = subscriberRepository.searchAllByOffset(criteria)
        assertEquals(subscribers, response.data.toList())
        coVerify(exactly = 1) {
            subscriberRegistratorR2dbcRepository.findAll(
                any(org.springframework.data.relational.core.query.Criteria::class),
                any(Pageable::class),
                eq(SubscriberEntity::class),
            )
        }
    }

    @Test
    fun `should search all BLOCKLISTED subscribers by criteria using cursor pagination`() = runBlocking {

        val criteria = Criteria.Equals("status", "BLOCKLISTED")

        val response = subscriberRepository.searchAllByCursor(criteria)
        assertEquals(subscribers, response.data.toList())
        coVerify(exactly = 1) {
            subscriberRegistratorR2dbcRepository.findAllByCursor(
                any(org.springframework.data.relational.core.query.Criteria::class),
                any(Int::class),
                eq(SubscriberEntity::class),
                any(org.springframework.data.domain.Sort::class),
                any(Cursor::class),
            )
        }
    }

    @Test
    fun `should search all DISABLED subscribers by criteria using offset pagination`() = runBlocking {

        val criteria = Criteria.Equals("status", "DISABLED")

        val response = subscriberRepository.searchAllByOffset(criteria)
        assertEquals(subscribers, response.data.toList())
        coVerify(exactly = 1) {
            subscriberRegistratorR2dbcRepository.findAll(
                any(org.springframework.data.relational.core.query.Criteria::class),
                any(Pageable::class),
                eq(SubscriberEntity::class),
            )
        }
    }

    @Test
    fun `should search all DISABLED subscribers by criteria using cursor pagination`() = runBlocking {

        val criteria = Criteria.Equals("status", "DISABLED")

        val response = subscriberRepository.searchAllByCursor(criteria)
        assertEquals(subscribers, response.data.toList())
        coVerify(exactly = 1) {
            subscriberRegistratorR2dbcRepository.findAllByCursor(
                any(org.springframework.data.relational.core.query.Criteria::class),
                any(Int::class),
                eq(SubscriberEntity::class),
                any(org.springframework.data.domain.Sort::class),
                any(Cursor::class),
            )
        }
    }

    @Test
    fun `should search by criteria with multiple filters using offset pagination`() = runBlocking {

        val criteria = Criteria.And(
            listOf(
                Criteria.Equals("email", "email"),
                Criteria.Equals("firstname", "firstname"),
                Criteria.Equals("lastname", "lastname"),
                Criteria.Equals("status", "status"),
            ),
        )

        val response = subscriberRepository.searchAllByOffset(criteria)
        assertEquals(subscribers, response.data.toList())
        coVerify(exactly = 1) {
            subscriberRegistratorR2dbcRepository.findAll(
                any(org.springframework.data.relational.core.query.Criteria::class),
                any(Pageable::class),
                eq(SubscriberEntity::class),
            )
        }
    }

    @Test
    fun `should search by criteria with multiple filters using cursor pagination`() = runBlocking {

        val criteria = Criteria.And(
            listOf(
                Criteria.Equals("email", "email"),
                Criteria.Equals("firstname", "firstname"),
                Criteria.Equals("lastname", "lastname"),
                Criteria.Equals("status", "status"),
            ),
        )

        val response = subscriberRepository.searchAllByCursor(criteria)
        assertEquals(subscribers, response.data.toList())
        coVerify(exactly = 1) {
            subscriberRegistratorR2dbcRepository.findAllByCursor(
                any(org.springframework.data.relational.core.query.Criteria::class),
                any(Int::class),
                eq(SubscriberEntity::class),
                any(org.springframework.data.domain.Sort::class),
                any(Cursor::class),
            )
        }
    }
    @Test
    fun `should search by criteria with multiple filters and sort using offset pagination`() = runBlocking {

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

        val response = subscriberRepository.searchAllByOffset(criteria, 10, 0, sort)
        assertEquals(subscribers, response.data.toList())
    }

    @Test
    fun `should search by criteria with multiple filters and sort using cursor pagination`() = runBlocking {

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
        coEvery {
            subscriberRegistratorR2dbcRepository.findAllByCursor(
                eq(criteriaParsed),
                eq(10),
                eq(SubscriberEntity::class),
                eq(sortCriteria),
                any(Cursor::class),
            )
        } returns Mono.just(
            CursorPageResponse(
                data = subscribers.map { it.toEntity() },
                nextPageCursor = TimestampCursor(
                    subscribers.last().createdAt,
                ).serialize(),
            ),
        )

        val response = subscriberRepository.searchAllByCursor(criteria, 10, sort)
        assertEquals(subscribers, response.data.toList())
    }
}
