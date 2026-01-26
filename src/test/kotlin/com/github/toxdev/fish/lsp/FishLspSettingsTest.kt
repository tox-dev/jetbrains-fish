package com.github.toxdev.fish.lsp

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FishLspSettingsTest {
    private lateinit var settings: FishLspSettings

    @BeforeEach
    fun setUp() {
        settings = FishLspSettings()
    }

    @Test
    fun `fishLspPath defaults to empty string`() {
        assertEquals("", settings.fishLspPath)
    }

    @Test
    fun `fishLspPath can be set and retrieved`() {
        settings.fishLspPath = "/usr/local/bin/fish-lsp"
        assertEquals("/usr/local/bin/fish-lsp", settings.fishLspPath)
    }

    @Test
    fun `notificationDismissed defaults to false`() {
        assertFalse(settings.notificationDismissed)
    }

    @Test
    fun `notificationDismissed can be set and retrieved`() {
        settings.notificationDismissed = true
        assertTrue(settings.notificationDismissed)
    }

    @Test
    fun `getState returns current state`() {
        settings.fishLspPath = "/path/to/fish-lsp"
        settings.notificationDismissed = true

        val state = settings.state
        assertEquals("/path/to/fish-lsp", state.fishLspPath)
        assertTrue(state.notificationDismissed)
    }

    @Test
    fun `loadState updates settings`() {
        val newState =
            FishLspSettings.State(
                fishLspPath = "/new/path",
                notificationDismissed = true,
            )
        settings.loadState(newState)

        assertEquals("/new/path", settings.fishLspPath)
        assertTrue(settings.notificationDismissed)
    }

    @Test
    fun `getEffectivePath returns configured path when set`() {
        settings.fishLspPath = "/custom/fish-lsp"
        assertEquals("/custom/fish-lsp", settings.getEffectivePath())
    }

    @Test
    fun `getEffectivePath searches PATH when fishLspPath is empty`() {
        settings.fishLspPath = ""
        val result = settings.getEffectivePath()
        assertTrue(result.isEmpty() || result.contains("fish-lsp"))
    }
}
