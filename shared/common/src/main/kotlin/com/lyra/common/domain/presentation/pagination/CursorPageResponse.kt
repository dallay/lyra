package com.lyra.common.domain.presentation.pagination

import com.lyra.common.domain.bus.query.Response

data class CursorPageResponse<T>(
    val data: Collection<T>,
    val prevPageCursor: String?,
    val nextPageCursor: String?,
) : Response

inline fun <T, U> CursorPageResponse<T>.map(
    func: (Collection<T>) -> Collection<U>
) = CursorPageResponse(
    data = func(data),
    prevPageCursor = prevPageCursor,
    nextPageCursor = nextPageCursor,
)
