package com.lyra.app.forms.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HexColorTest {

    @Test
    fun `validates correct hex color`() {
        val hexColor = HexColor("#FFFFFF")
        assertEquals("#FFFFFF", hexColor.hex)
    }

    @Test
    fun `validates correct short hex color`() {
        val hexColor = HexColor("#FFF")
        assertEquals("#FFF", hexColor.hex)
    }

    @Test
    fun `throws exception for invalid hex color`() {
        val exception = assertThrows<IllegalArgumentException> {
            HexColor("#GGGGGG")
        }
        assertEquals("Invalid hexadecimal color code: #GGGGGG", exception.message)
    }

    @Test
    fun `throws exception for invalid short hex color`() {
        val exception = assertThrows<IllegalArgumentException> {
            HexColor("#GGG")
        }
        assertEquals("Invalid hexadecimal color code: #GGG", exception.message)
    }

    @Test
    fun `throws exception for hex color without hash`() {
        val exception = assertThrows<IllegalArgumentException> {
            HexColor("aafff")
        }
        assertEquals("Invalid hexadecimal color code: aafff", exception.message)
    }

    @Test
    fun `throws exception for short hex color without hash`() {
        val exception = assertThrows<IllegalArgumentException> {
            HexColor("aaw")
        }
        assertEquals("Invalid hexadecimal color code: aaw", exception.message)
    }
}
