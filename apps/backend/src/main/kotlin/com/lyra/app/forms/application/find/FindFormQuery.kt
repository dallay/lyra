package com.lyra.app.forms.application.find

import com.lyra.app.forms.application.FormResponse
import com.lyra.common.domain.bus.query.Query

class FindFormQuery(
    val id: String
) : Query<FormResponse> {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FindFormQuery) return false
        return true
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
