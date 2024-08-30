package com.lyra.app.forms.application.details

import com.lyra.app.forms.application.FormResponse
import com.lyra.common.domain.bus.query.Query

/**
 * Query for fetching form details.
 *
 * @property formId The ID of the form to fetch.
 */
data class DetailFormQuery(
    val formId: String
) : Query<FormResponse>
