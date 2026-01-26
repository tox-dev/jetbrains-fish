package com.github.toxdev.fish.lsp

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FishLspNotificationTest {
    private lateinit var settings: FishLspSettings

    @BeforeEach
    fun setUp() {
        settings = FishLspSettings()
    }

    @Test
    fun `settings notificationDismissed defaults to false`() {
        assertFalse(settings.notificationDismissed)
    }

    @Test
    fun `settings notificationDismissed can be toggled`() {
        settings.notificationDismissed = true
        assertTrue(settings.notificationDismissed)

        settings.notificationDismissed = false
        assertFalse(settings.notificationDismissed)
    }

    @Test
    fun `notification group id is correct`() {
        assertEquals("Fish LSP", FishLspNotification.NOTIFICATION_GROUP_ID)
    }
}
