package com.lyra.common.domain.presentation.pagination

enum class LogicalOperator {
    AND, OR
}
data class FilterCondition(val operator: LogicalOperator, val values: List<String>) {
    override fun toString(): String = "$operator:${values.joinToString(separator = ",")}"
}

open class BaseRequestPageable(
    open var size: Int = DEFAULT_PAGE_SIZE,
    open val search: String? = null,
    open val filter: MutableMap<String, FilterCondition> = mutableMapOf(),
    open val sort: List<String>? = emptyList()
) {
    companion object {
        const val DEFAULT_PAGE_SIZE = 10
    }
}

data class OffsetRequestPageable(
    override var size: Int = DEFAULT_PAGE_SIZE,
    override val search: String? = null,
    override val filter: MutableMap<String, FilterCondition> = mutableMapOf(),
    override val sort: List<String>? = emptyList(),
    val page: Int? = 0
) : BaseRequestPageable(size, search, filter, sort)

data class CursorRequestPageable(
    override var size: Int = DEFAULT_PAGE_SIZE,
    override val search: String? = null,
    override val filter: MutableMap<String, FilterCondition> = mutableMapOf(),
    override val sort: List<String>? = emptyList(),
    val cursor: String? = null
) : BaseRequestPageable(size, search, filter, sort)
