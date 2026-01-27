package com.github.toxdev.fish.highlighting

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FishColorSettingsPageTest {
    private val page = FishColorSettingsPage()

    @Test
    fun `getIcon returns non-null icon`() {
        assertNotNull(page.icon)
    }

    @Test
    fun `getHighlighter returns FishSyntaxHighlighter`() {
        val highlighter = page.highlighter
        assertNotNull(highlighter)
        assertTrue(highlighter is FishSyntaxHighlighter)
    }

    @Test
    fun `getDemoText returns non-empty string`() {
        val demoText = page.demoText
        assertNotNull(demoText)
        assertTrue(demoText.isNotEmpty())
        assertTrue(demoText.contains("function"))
        assertTrue(demoText.contains("#!/usr/bin/env fish"))
    }

    @Test
    fun `getAdditionalHighlightingTagToDescriptorMap returns null`() {
        assertNull(page.additionalHighlightingTagToDescriptorMap)
    }

    @Test
    fun `getAttributeDescriptors returns non-empty array`() {
        val descriptors = page.attributeDescriptors
        assertNotNull(descriptors)
        assertTrue(descriptors.isNotEmpty())
        assertEquals(15, descriptors.size)
    }

    @Test
    fun `getColorDescriptors returns empty array`() {
        val descriptors = page.colorDescriptors
        assertNotNull(descriptors)
        assertEquals(0, descriptors.size)
    }

    @Test
    fun `getDisplayName returns Fish`() {
        assertEquals("Fish", page.displayName)
    }
}
