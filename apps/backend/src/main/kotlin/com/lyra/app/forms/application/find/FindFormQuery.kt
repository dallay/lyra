package com.lyra.app.forms.application.find

import com.lyra.app.forms.application.FormResponse
import com.lyra.common.domain.bus.query.Query

data class FindFormQuery(
    val organizationId: String,
    val formId: String
) : Query<FormResponse>
