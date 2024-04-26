package com.lyra.app.forms.application.search

import com.lyra.app.forms.application.FormResponse
import com.lyra.app.forms.domain.FormFinderRepository
import com.lyra.common.domain.Service
import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.common.domain.presentation.pagination.TimestampCursor
import com.lyra.common.domain.presentation.pagination.map
import com.lyra.common.domain.presentation.sort.Sort
import org.slf4j.LoggerFactory

/**
 * This class represents a service to search forms.
 *
 * @property repository The [FormFinderRepository] to fetch forms from.
 */
@Service
class SearchFormsSearcher(private val repository: FormFinderRepository) {

    /**
     * Searches for forms based on the given criteria, size, cursor, and sort.
     *
     * @param criteria The criteria to filter the forms. It can be null.
     * @param size The size of the page to return. It can be null.
     * @param cursor The cursor to paginate through the forms. It can be null.
     * @param sort The sort order for the forms. It can be null.
     * @return A page of forms that match the given criteria, size, cursor, and sort.
     */
    suspend fun search(
        criteria: Criteria?,
        size: Int?,
        cursor: String?,
        sort: Sort?
    ): CursorPageResponse<FormResponse> {
        log.debug(
            "Searching all forms with criteria: {}, size: {}, cursor: {}, sort: {}",
            criteria,
            size,
            cursor,
            sort,
        )
        val timestampCursor = cursor?.let { TimestampCursor.deserialize(it) }
        return repository.search(criteria, size, sort, timestampCursor).map { forms ->
            forms.map { FormResponse.from(it) }
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(SearchFormsSearcher::class.java)
    }
}
