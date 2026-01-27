package com.github.toxdev.fish.lsp4ij

import com.github.toxdev.fish.lsp.FishLspSettings
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.testFramework.junit5.TestApplication
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@TestApplication
class FishLanguageServerTest {
    private lateinit var settings: FishLspSettings

    @BeforeEach
    fun setUp() {
        settings = FishLspSettings()
        settings.fishLspPath = "fish-lsp"
        mockkObject(FishLspSettings.Companion)
        every { FishLspSettings.getInstance() } returns settings
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(FishLspSettings.Companion)
    }

    @Test
    fun `FishLanguageServer class exists`() {
        assertNotNull(FishLanguageServer::class)
    }

    @Test
    fun `FishLanguageServer extends OSProcessStreamConnectionProvider`() {
        val superClass = FishLanguageServer::class.java.superclass
        assertEquals("OSProcessStreamConnectionProvider", superClass.simpleName)
    }

    @Test
    fun `constructor creates instance with command line`() {
        val server = FishLanguageServer()
        assertNotNull(server)
        assertNotNull(server.commandLine)
    }

    @Test
    fun `command line contains fish-lsp path`() {
        val server = FishLanguageServer()
        val commandLine = server.commandLine

        assertTrue(commandLine.commandLineString.contains("fish-lsp"))
    }

    @Test
    fun `command line includes start and stdio arguments`() {
        val server = FishLanguageServer()
        val commandLine = server.commandLine

        assertTrue(commandLine.commandLineString.contains("start"))
        assertTrue(commandLine.commandLineString.contains("--stdio"))
    }

    @Test
    fun `command line uses console parent environment`() {
        val server = FishLanguageServer()
        val commandLine = server.commandLine

        assertEquals(
            GeneralCommandLine.ParentEnvironmentType.CONSOLE,
            commandLine.parentEnvironmentType,
        )
    }
}
