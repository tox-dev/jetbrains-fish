package com.github.toxdev.fish.lsp4ij

import com.github.toxdev.fish.lsp.FishLspSettings
import com.intellij.openapi.project.Project
import com.intellij.testFramework.junit5.TestApplication
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@TestApplication
class FishLanguageServerFactoryTest {
    private lateinit var settings: FishLspSettings
    private lateinit var project: Project

    @BeforeEach
    fun setUp() {
        settings = FishLspSettings()
        settings.fishLspPath = "fish-lsp"
        project = mockk(relaxed = true)
        mockkObject(FishLspSettings.Companion)
        every { FishLspSettings.getInstance() } returns settings
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(FishLspSettings.Companion)
    }

    @Test
    fun `factory is instantiable`() {
        val factory = FishLanguageServerFactory()
        assertNotNull(factory)
    }

    @Test
    fun `factory implements LanguageServerFactory`() {
        val interfaces = FishLanguageServerFactory::class.java.interfaces.map { it.simpleName }
        assertEquals(listOf("LanguageServerFactory"), interfaces)
    }

    @Test
    fun `createConnectionProvider returns FishLanguageServer`() {
        val factory = FishLanguageServerFactory()
        val provider = factory.createConnectionProvider(project)

        assertNotNull(provider)
        assertTrue(provider is FishLanguageServer)
    }
}
