package com.lyra.common.domain.presentation.pagination

import com.lyra.common.domain.bus.query.Response

data class OffsetPageResponse<T>(
    val data: Collection<T>,
    val total: Long? = null,
    val perPage: Int,
    val page: Int? = null,
    val totalPages: Int? = null,
) : Response

inline fun <T, U> OffsetPageResponse<T>.map(
    func: (Collection<T>) -> Collection<U>
) = OffsetPageResponse(
    data = func(data),
    total = total,
    perPage = perPage,
    page = page,
    totalPages = totalPages,
)
