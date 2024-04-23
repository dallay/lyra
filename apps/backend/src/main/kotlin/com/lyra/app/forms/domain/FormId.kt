package com.lyra.app.forms.domain

import com.lyra.common.domain.BaseId
import java.util.*

data class FormId(private val id: UUID) : BaseId<UUID>(id) {
    constructor(id: String) : this(UUID.fromString(id))
    companion object {
        fun create() = FormId(UUID.randomUUID())
    }
}
