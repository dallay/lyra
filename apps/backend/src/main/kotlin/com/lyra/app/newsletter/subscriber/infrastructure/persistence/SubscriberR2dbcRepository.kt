package com.lyra.app.newsletter.subscriber.infrastructure.persistence

import com.lyra.app.newsletter.subscriber.domain.Subscriber
import com.lyra.app.newsletter.subscriber.domain.SubscriberRepository
import com.lyra.app.newsletter.subscriber.domain.SubscriberSearchRepository
import com.lyra.app.newsletter.subscriber.domain.SubscriberStatsRepository
import com.lyra.app.newsletter.subscriber.domain.SubscriberStatus
import com.lyra.app.newsletter.subscriber.domain.exceptions.SubscriberException
import com.lyra.app.newsletter.subscriber.infrastructure.persistence.entity.SubscriberEntity
import com.lyra.app.newsletter.subscriber.infrastructure.persistence.mapper.SubscriberMapper.toDomain
import com.lyra.app.newsletter.subscriber.infrastructure.persistence.mapper.SubscriberMapper.toEntity
import com.lyra.app.newsletter.subscriber.infrastructure.persistence.repository.SubscriberReactiveR2dbcRepository
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.pagination.Cursor
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.common.domain.presentation.pagination.OffsetPageResponse
import com.lyra.common.domain.presentation.pagination.TimestampCursor
import com.lyra.common.domain.presentation.sort.Sort
import com.lyra.spring.boot.presentation.sort.toSpringSort
import com.lyra.spring.boot.repository.R2DBCCriteriaParser
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Sort as SpringSort

private const val DEFAULT_LIMIT = 10

@Repository
class SubscriberR2dbcRepository(
    private val subscriberReactiveR2DbcRepository: SubscriberReactiveR2dbcRepository,
) : SubscriberRepository, SubscriberSearchRepository, SubscriberStatsRepository {
    private val criteriaParser = R2DBCCriteriaParser(SubscriberEntity::class)

    override suspend fun create(subscriber: Subscriber) {
        try {
            subscriberReactiveR2DbcRepository.save(subscriber.toEntity())
        } catch (e: DuplicateKeyException) {
            log.error("Form already exists in the database: ${subscriber.id.value}")
            throw SubscriberException("Error creating subscriber", e)
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

        val pageable = PageRequest.of(page ?: 0, size ?: DEFAULT_LIMIT, sortCriteria)

        val pageEntity = subscriberReactiveR2DbcRepository.findAll(
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
        val pageResponse = subscriberReactiveR2DbcRepository.findAllByCursor(
            criteriaParsed,
            size ?: DEFAULT_LIMIT,
            SubscriberEntity::class,
            springSort,
            cursor ?: TimestampCursor.DEFAULT_CURSOR,
        )

        val content = pageResponse.data.map { it.toDomain() }
        return CursorPageResponse(content, pageResponse.prevPageCursor, pageResponse.nextPageCursor)
    }

    override suspend fun searchActive(): List<Subscriber> {
        return subscriberReactiveR2DbcRepository.findAllByStatus(SubscriberStatus.ENABLED)
            .map { it.toDomain() }
    }

    /**
     * Search all the subscribers in the list of emails.
     * @param emails The set of emails to search for.
     * @param organizationId The identifier of the organization the subscribers belong to.
     * @return A List of subscribers.
     */
    override suspend fun searchAllByEmails(
        organizationId: OrganizationId,
        emails: Set<String>
    ): List<Subscriber> {
        log.debug("Searching all subscribers by emails: {} for organization: {}", emails, organizationId)
        return subscriberReactiveR2DbcRepository.findAllByEmails(organizationId.value, emails).map { it.toDomain() }
    }

    /**
     * Count subscribers by their status.
     *
     * This method returns a flow of pairs, where each pair consists of a status
     * (as a string) and the count of subscribers with that status (as an integer).
     *
     * @return List<Pair<String, Long>> A flow emitting pairs of status and count.
     */
    override suspend fun countByStatus(organizationId: OrganizationId): List<Pair<String, Long>> =
        subscriberReactiveR2DbcRepository.countByStatus(organizationId.value).map { (status, count) ->
            status to count
        }

    /**
     * Count subscribers by tags.
     *
     * This method returns a flow of pairs, where each pair consists of a tag
     * (as a string) and the count of subscribers with that tag (as an integer).
     *
     * @param organizationId The ID of the organization to count subscribers for.
     * @return List<Pair<String, Long>> A flow emitting pairs of tag and count.
     */
    override suspend fun countByTag(organizationId: OrganizationId): List<Pair<String, Long>> {
        return subscriberReactiveR2DbcRepository.countByTag(organizationId.value).map { (tag, count) ->
            tag to count
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(SubscriberR2dbcRepository::class.java)
    }
}
