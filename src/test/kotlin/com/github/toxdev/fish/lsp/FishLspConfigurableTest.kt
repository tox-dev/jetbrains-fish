package com.github.toxdev.fish.lsp

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FishLspConfigurableTest {
    private lateinit var settings: FishLspSettings

    @BeforeEach
    fun setUp() {
        clearAllMocks()
        settings = mockk(relaxed = true)

        // Mock the settings accessor directly rather than the global ApplicationManager, which would
        // hand a relaxed mock to platform background coroutines and crash them.
        mockkObject(FishLspSettings.Companion)
        every { FishLspSettings.getInstance() } returns settings
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
