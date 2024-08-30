package com.lyra.app.forms.application.find

import com.lyra.app.forms.domain.Form
import com.lyra.app.forms.domain.FormFinderRepository
import com.lyra.app.forms.domain.FormId
import com.lyra.app.forms.domain.exception.FormNotFoundException
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.Service
import org.slf4j.LoggerFactory

/**
 * This is a service class that handles the finding of forms.
 * It uses a [FormFinderRepository] to find a form by its ID.
 *
 * @property finder [FormFinderRepository] The repository to use for finding forms.
 */
@Service
class FormFinder(private val finder: FormFinderRepository) {
    /**
     * This function is used to find a form by its ID.
     *
     * @param organizationId The organization ID of the form to find.
     * @param formId The ID of the form to find.
     * @return The form if found, or throws a [FormNotFoundException] if not found.
     */
    suspend fun find(organizationId: OrganizationId, formId: FormId): Form? {
        log.debug("Finding form with ids: {}, {}", organizationId, formId)
        return finder.findByFormIdAndOrganizationId(organizationId = organizationId, formId = formId)
    }

    companion object {
        private val log = LoggerFactory.getLogger(FormFinder::class.java)
    }
}
