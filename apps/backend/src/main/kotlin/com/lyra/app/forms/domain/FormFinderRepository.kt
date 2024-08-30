package com.lyra.app.forms.domain

import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.criteria.Criteria
import com.lyra.common.domain.presentation.pagination.Cursor
import com.lyra.common.domain.presentation.pagination.CursorPageResponse
import com.lyra.common.domain.presentation.sort.Sort

/**
 * This is an interface for a repository that finds or search forms.
 */
interface FormFinderRepository {
    /**
     * This function is used to find a form by its id.
     *
     * @param id The id of the form to find.
     * @return The form if found, or null if not found.
     */
    suspend fun findById(id: FormId): Form?

    /**
     * Find a form by form id and organization id.
     * @param formId The form id.
     * @param organizationId The organization id.
     * @return The form if found, or null if not found.
     */
    suspend fun findByFormIdAndOrganizationId(formId: FormId, organizationId: OrganizationId): Form?

    /**
     * This function is used to search all [Form] by cursor.
     *
     * @param criteria The criteria to use for the search.
     * @param size The size of the page to return.
     * @param sort The sort order to use for the results.
     * @param cursor The cursor to use for the search.
     * @return A CursorPageResponse containing the search results.
     */
    suspend fun search(
        criteria: Criteria? = null,
        size: Int? = null,
        sort: Sort? = null,
        cursor: Cursor? = null,
    ): CursorPageResponse<Form>
}
