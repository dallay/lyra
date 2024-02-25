package com.lyra.common.domain.presentation.sort

enum class Direction {
    ASC, DESC;
    fun fromString(direction: String): Direction = Direction.fromString(direction)
    companion object {
        fun fromString(direction: String): Direction {
            return when (direction) {
                "asc" -> ASC
                "desc" -> DESC
                else -> throw IllegalArgumentException("Invalid direction: $direction")
            }
        }
    }
}

data class Order(val direction: Direction, val property: String) {
    override fun toString(): String = "$property: $direction"

    companion object {
        fun desc(property: String): Order = Order(Direction.DESC, property)
        fun asc(property: String): Order = Order(Direction.ASC, property)
    }
}

class Sort(val orders: List<Order>) {

    fun and(sort: Sort): Sort = Sort(this.orders + sort.orders)

    fun ascending(): Sort = Sort(orders.map { it.copy(direction = Direction.ASC) })

    fun descending(): Sort = Sort(orders.map { it.copy(direction = Direction.DESC) })
    fun getOrderFor(property: String): Order? = orders.find { it.property == property }
    override fun toString(): String = orders.joinToString(", ") { "${it.property}: ${it.direction}" }

    companion object {
        val DEFAULT_DIRECTION = Direction.ASC

        fun by(vararg properties: String): Sort {
            val orders = properties.map { Order(DEFAULT_DIRECTION, it) }
            return Sort(orders)
        }

        fun by(direction: Direction, vararg properties: String): Sort {
            val orders = properties.map { Order(direction, it) }
            return Sort(orders)
        }
    }
}
