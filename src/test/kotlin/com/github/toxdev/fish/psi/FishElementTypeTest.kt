package com.github.toxdev.fish.psi

import com.github.toxdev.fish.FishLanguage
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class FishElementTypeTest {
    @Test
    fun `FishElementType has FishLanguage`() {
        val elementType = FishElementType("TEST")
        assertEquals(FishLanguage.INSTANCE, elementType.language)
    }

    @Test
    fun `toString returns formatted string`() {
        val elementType = FishElementType("MY_ELEMENT")
        assertEquals("FishElementType.MY_ELEMENT", elementType.toString())
    }

    @Test
    fun `FishElementType can be instantiated`() {
        val elementType = FishElementType("ELEMENT")
        assertNotNull(elementType)
    }
}
