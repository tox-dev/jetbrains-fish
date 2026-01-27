package com.github.toxdev.fish.lsp

import com.intellij.openapi.application.Application
import com.intellij.openapi.application.ApplicationManager
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FishLspConfigurableTest {
    private lateinit var application: Application
    private lateinit var settings: FishLspSettings

    @BeforeEach
    fun setUp() {
        clearAllMocks()
        application = mockk(relaxed = true)
        settings = mockk(relaxed = true)

        mockkStatic(ApplicationManager::class)
        every { ApplicationManager.getApplication() } returns application
        every { application.getService(FishLspSettings::class.java) } returns settings
        every { settings.fishLspPath } returns ""
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `FishLspConfigurable class exists`() {
        assertNotNull(FishLspConfigurable::class)
    }

    @Test
    fun `getDisplayName returns Fish Shell`() {
        val configurable = FishLspConfigurable()
        assertEquals("Fish Shell", configurable.displayName)
    }
}
