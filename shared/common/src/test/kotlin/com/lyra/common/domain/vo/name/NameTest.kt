package com.lyra.common.domain.vo.name

import java.util.stream.Stream
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class NameTest {

    @Test
    fun `should create a valid name and lastname`() {
        val names = mapOf(
            "Yuniel" to "Acosta Pérez",
            "Neil" to "O'Neil",
            "Ramón" to "González Pérez",
            "Ñico" to "López",
        )

        names.forEach { (firstname, lastname) ->
            println("Fullname: $firstname $lastname")
            val name = Name(firstname, lastname)
            assertEquals(firstname, name.firstName.value)
            assertEquals(lastname, name.lastName?.value)
            assertEquals(name.fullName(), "$firstname $lastname")
        }
    }

    @Test
    fun `should not build without firstname`() {
        assertThrows(FirstNameNotValidException::class.java) {
            Name("", "Acosta Pérez")
        }
    }

    @Test
    fun `should not build with empty lastname`() {
        assertThrows(LastNameNotValidException::class.java) {
            Name("Yuniel", "")
        }
    }
    @Test
    fun `should build without lastname`() {
        val name = Name("Yuniel", null)
        assertEquals("Yuniel", name.firstName.value)
        assertEquals(null, name.lastName?.value)
        assertEquals("Yuniel", name.fullName())
    }

    @Test
    fun `should not build with firstname greater than 150 characters`() {
        val firstname = (1..256).joinToString("") { "a" }
        assertThrows(FirstNameNotValidException::class.java) {
            Name(firstname, "Acosta Pérez")
        }
    }

    @Test
    fun `should not build with lastname greater than 150 characters`() {
        val lastname = (1..256).joinToString("") { "a" }
        assertThrows(LastNameNotValidException::class.java) {
            Name("Yuniel", lastname)
        }
    }

    @Test
    fun `should not build with firstname and lastname greater than 150 characters`() {
        val firstname = (1..256).joinToString("") { "a" }
        val lastname = (1..256).joinToString("") { "a" }
        assertThrows(FirstNameNotValidException::class.java) {
            Name(firstname, lastname)
        }
    }

    @Test
    fun `should get fullname`() {
        val name = Name("Yuniel", "Acosta Pérez")
        assertEquals("Yuniel Acosta Pérez", name.fullName())
    }

    @Test
    fun shouldSortNames() {
        val names: List<Name> = Stream
            .of(Name("paul", "Dupond"), Name("jean", "Dupont"), Name("jean", "Dupond"))
            .sorted()
            .toList()

        assertEquals(names[0], Name("jean", "Dupond"))
        assertEquals(names[1], Name("jean", "Dupont"))
        assertEquals(names[2], Name("paul", "Dupond"))
    }
}
