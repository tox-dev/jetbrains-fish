package com.github.toxdev.fish.lsp

import com.intellij.notification.Notification
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
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

    @Test
    fun `FishLspNotification object exists`() {
        assertNotNull(FishLspNotification)
    }
}

class FishLspNotificationFunctionTest {
    private lateinit var settings: FishLspSettings
    private lateinit var project: Project
    private lateinit var notificationGroupManager: NotificationGroupManager
    private lateinit var notificationGroup: NotificationGroup
    private lateinit var notification: Notification

    @BeforeEach
    fun setUp() {
        clearAllMocks()
        settings = FishLspSettings()
        project = mockk(relaxed = true)
        notificationGroupManager = mockk(relaxed = true)
        notificationGroup = mockk(relaxed = true)
        notification = mockk(relaxed = true)

        // Mock only the plugin's own singletons. Replacing the global ApplicationManager here would
        // hand a relaxed mock to unrelated platform background coroutines, which then fail casting
        // its default return values and abort this test.
        mockkObject(FishLspSettings.Companion)
        mockkStatic(NotificationGroupManager::class)
        every { FishLspSettings.getInstance() } returns settings
        every { NotificationGroupManager.getInstance() } returns notificationGroupManager
        every { notificationGroupManager.getNotificationGroup(any()) } returns notificationGroup
        every { notificationGroup.createNotification(any<String>(), any<String>(), any<NotificationType>()) } returns notification
        every { notification.addAction(any()) } returns notification
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `notifyFishLspNotFound returns early when notification dismissed`() {
        settings.notificationDismissed = true

        FishLspNotification.notifyFishLspNotFound(project)

        verify(exactly = 0) { notificationGroupManager.getNotificationGroup(any()) }
    }

    @Test
    fun `notifyFishLspNotFound adds three actions`() {
        settings.notificationDismissed = false

        FishLspNotification.notifyFishLspNotFound(project)

        verify(exactly = 3) { notification.addAction(any()) }
    }
}
