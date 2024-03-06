package com.lyra.app.newsletter.domain

data class Name(val firstName: FirstName, val lastName: LastName?) : Comparable<Name> {

    constructor(firstName: String, lastName: String?) : this(
        FirstName(firstName),
        lastName?.let { LastName(it) },
    )

    /**
     * Returns the full name of the user (first name + last name)
     * @return the full name of the user
     */
    fun fullName(): String = "${firstName.value} ${lastName?.value ?: ""}".trim()

    /**
     * Compares this object with the specified object for order. Returns zero if this object is equal
     * to the specified [other] object, a negative number if it's less than [other], or a positive number
     * if it's greater than [other].
     */
    override operator fun compareTo(other: Name): Int = fullName().compareTo(other.fullName())
}
