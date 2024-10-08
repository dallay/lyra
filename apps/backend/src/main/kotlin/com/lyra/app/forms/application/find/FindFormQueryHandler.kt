package com.lyra.app.forms.application.find

import com.lyra.app.forms.application.FormResponse
import com.lyra.app.forms.domain.FormId
import com.lyra.app.forms.domain.exception.FormNotFoundException
import com.lyra.app.organization.domain.OrganizationId
import com.lyra.common.domain.Service
import com.lyra.common.domain.bus.query.QueryHandler
import org.slf4j.LoggerFactory

/**
 * Service class responsible for handling form find queries.
 *
 * @property finder [FormFinder] The service for finding forms.
 */
@Service
class FindFormQueryHandler(
    private val finder: FormFinder
) : QueryHandler<FindFormQuery, FormResponse> {

    /**
     * Handles a form find query.
     * Logs the id of the form being found, finds the form using the finder service, and returns a [FormResponse].
     * If the form is not found, a [FormNotFoundException] is thrown.
     *
     * @param query The form find query to handle.
     * @return The [FormResponse] for the xfound form.
     * @throws [FormNotFoundException] If the form is not found.
     */
    override suspend fun handle(query: FindFormQuery): FormResponse {
        log.debug("Finding form with ids: ${query.organizationId}, ${query.formId}")
        val formId = FormId(query.formId)
        val organizationId = OrganizationId(query.organizationId)
        val form = finder.find(organizationId, formId) ?: throw FormNotFoundException("Form not found")
        return FormResponse.from(form)
    }

    companion object {
        private val log = LoggerFactory.getLogger(FindFormQueryHandler::class.java)
    }
}
