package com.lyra.common.domain.presentation.pagination

import com.lyra.common.domain.presentation.PageResponse

data class OffsetPageResponse<T>(
    override val data: Collection<T>,
    val total: Long? = null,
    val perPage: Int,
    val page: Int? = null,
    val totalPages: Int? = null,
) : PageResponse<T>(data)

inline fun <T, U> OffsetPageResponse<T>.map(
    func: (Collection<T>) -> Collection<U>
) = OffsetPageResponse(
    data = func(data),
    total = total,
    perPage = perPage,
    page = page,
    totalPages = totalPages,
)
