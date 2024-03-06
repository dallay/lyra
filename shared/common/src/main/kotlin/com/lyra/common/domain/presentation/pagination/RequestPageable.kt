package com.lyra.common.domain.presentation.pagination
open class BaseRequestPageable(
    open var size: Int = DEFAULT_PAGE_SIZE,
    open val search: String? = null,
    open val filter: MutableMap<String, List<String>> = mutableMapOf(),
    open val sort: List<String>? = emptyList()
) {
    companion object {
        const val DEFAULT_PAGE_SIZE = 10
    }
}

data class OffsetRequestPageable(
    override var size: Int = DEFAULT_PAGE_SIZE,
    override val search: String? = null,
    override val filter: MutableMap<String, List<String>> = mutableMapOf(),
    override val sort: List<String>? = emptyList(),
    val page: Int? = 0
) : BaseRequestPageable(size, search, filter, sort)

data class CursorRequestPageable(
    override var size: Int = DEFAULT_PAGE_SIZE,
    override val search: String? = null,
    override val filter: MutableMap<String, List<String>> = mutableMapOf(),
    override val sort: List<String>? = emptyList(),
    val cursor: String? = null
) : BaseRequestPageable(size, search, filter, sort)
