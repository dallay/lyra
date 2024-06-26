package com.lyra.app.newsletter.infrastructure.persistence

import com.lyra.app.newsletter.domain.Subscriber
import com.lyra.app.newsletter.domain.SubscriberRepository
import com.lyra.app.newsletter.domain.SubscriberStatus
import com.lyra.app.newsletter.domain.exceptions.SubscriberException
import com.lyra.app.newsletter.infrastructure.persistence.entity.SubscriberEntity
import com.lyra.app.newsletter.infrastructure.persistence.mapper.SubscriberMapper.toDomain
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Sort as SpringSort

private const val DEFAULT_LIMIT = 10

@Repository
class SubscriberR2dbcRepository(
    private val subscriberRegistratorR2dbcRepository: SubscriberRegistratorR2dbcRepository,
) : SubscriberRepository {
    private val criteriaParser = R2DBCCriteriaParser(SubscriberEntity::class)
    override suspend fun create(subscriber: Subscriber) {
        try {
            subscriberRegistratorR2dbcRepository.save(subscriber.toEntity())
        } catch (e: DuplicateKeyException) {
            log.error("Form already exists in the database: ${subscriber.id.value}")
            throw SubscriberException("Error creating form", e)
        }
    }

    override suspend fun searchAllByOffset(
        criteria: Criteria?,
        size: Int?,
        page: Int?,
        sort: Sort?
    ): OffsetPageResponse<Subscriber> {
        log.debug(
            "Get all subscribers with filters: {} and sort: {} and pagination: size={}, page={}",
            criteria,
            sort,
            size,
            page,
        )
        val criteriaParsed = criteriaParser.parse(criteria ?: Criteria.Empty)
        val sortCriteria = sort.toSpringSort()
        // Pageable paging = PageRequest.of(page, size);
        val pageable = PageRequest.of(page ?: 0, size ?: DEFAULT_LIMIT, sortCriteria)

        val pageEntity = subscriberRegistratorR2dbcRepository.findAll(
            criteriaParsed,
            pageable,
            SubscriberEntity::class,
        )

        return OffsetPageResponse(
            data = pageEntity.content.map { it.toDomain() },
            total = pageEntity.totalElements,
            perPage = pageEntity.size,
            page = pageEntity.number,
            totalPages = pageEntity.totalPages,
        )
    }

    override suspend fun searchAllByCursor(
        criteria: Criteria?,
        size: Int?,
        sort: Sort?,
        cursor: Cursor?
    ): CursorPageResponse<Subscriber> {
        log.debug(
            "Get all subscribers with filters: {} and sort: {} and pagination: size={}, cursor={}",
            criteria,
            sort,
            size,
            cursor,
        )
        val criteriaParsed = criteriaParser.parse(criteria ?: Criteria.Empty)
        val springSort = sort?.toSpringSort() ?: SpringSort.unsorted()
        val pageResponse = subscriberRegistratorR2dbcRepository.findAllByCursor(
            criteriaParsed,
            size ?: DEFAULT_LIMIT,
            SubscriberEntity::class,
            springSort,
            cursor ?: TimestampCursor.DEFAULT_CURSOR,
        )

        val content = pageResponse.data.map { it.toDomain() }
        return CursorPageResponse(content, pageResponse.nextPageCursor)
    }

    override suspend fun searchActive(): Flow<Subscriber> {
        return subscriberRegistratorR2dbcRepository.findAllByStatus(SubscriberStatus.ENABLED)
            .map { it.toDomain() }
    }

    companion object {
        private val log = LoggerFactory.getLogger(SubscriberR2dbcRepository::class.java)
    }
}
