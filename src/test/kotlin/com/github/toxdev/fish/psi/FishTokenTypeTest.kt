package com.github.toxdev.fish.psi

import com.github.toxdev.fish.FishLanguage
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class FishTokenTypeTest {
    @Test
    fun `FishTokenType has FishLanguage`() {
        val tokenType = FishTokenType("TEST")
        assertEquals(FishLanguage.INSTANCE, tokenType.language)
    }

    @Test
    fun `toString returns formatted string`() {
        val tokenType = FishTokenType("MY_TOKEN")
        assertEquals("FishTokenType.MY_TOKEN", tokenType.toString())
    }

    @Test
    fun `FishTokenType can be instantiated`() {
        val tokenType = FishTokenType("TOKEN")
        assertNotNull(tokenType)
    }
}
