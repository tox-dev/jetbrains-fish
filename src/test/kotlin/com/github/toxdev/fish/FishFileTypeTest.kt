package com.github.toxdev.fish

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test

class FishFileTypeTest {
    @Test
    fun `INSTANCE is not null`() {
        assertNotNull(FishFileType.INSTANCE)
    }

    @Test
    fun `INSTANCE is singleton`() {
        assertSame(FishFileType.INSTANCE, FishFileType.INSTANCE)
    }

    @Test
    fun `getName returns Fish`() {
        assertEquals("Fish", FishFileType.INSTANCE.name)
    }

    @Test
    fun `getDescription returns Fish shell script`() {
        assertEquals("Fish shell script", FishFileType.INSTANCE.description)
    }

    @Test
    fun `getDefaultExtension returns fish`() {
        assertEquals("fish", FishFileType.INSTANCE.defaultExtension)
    }

    @Test
    fun `getIcon returns non-null icon`() {
        assertNotNull(FishFileType.INSTANCE.icon)
    }

    @Test
    fun `getLanguage returns FishLanguage`() {
        assertSame(FishLanguage.INSTANCE, FishFileType.INSTANCE.language)
    }
}
