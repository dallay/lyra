package com.lyra.common.domain.presentation

import com.lyra.common.domain.bus.query.Response

open class PageResponse<T>(
    open val data: Collection<T>
) : Response {
    constructor() : this(emptyList())
}
