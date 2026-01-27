package com.github.toxdev.fish

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test

class FishLanguageTest {
    @Test
    fun `INSTANCE is not null`() {
        assertNotNull(FishLanguage.INSTANCE)
    }

    @Test
    fun `INSTANCE is singleton`() {
        assertSame(FishLanguage.INSTANCE, FishLanguage.INSTANCE)
    }

    @Test
    fun `getDisplayName returns Fish`() {
        assertEquals("Fish", FishLanguage.INSTANCE.displayName)
    }

    @Test
    fun `id is Fish`() {
        assertEquals("Fish", FishLanguage.INSTANCE.id)
    }
}
