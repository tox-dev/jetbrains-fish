package com.github.toxdev.fish.lsp

import com.github.toxdev.fish.FishFileType
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.junit5.TestApplication
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@TestApplication
class FishLspServerDescriptorTest {
    private lateinit var descriptor: FishLspServerDescriptor
    private lateinit var settings: FishLspSettings

    @BeforeEach
    fun setUp() {
        settings = FishLspSettings()
        settings.fishLspPath = "fish-lsp"
        mockkObject(FishLspSettings.Companion)
        every { FishLspSettings.getInstance() } returns settings

        val project = ProjectManager.getInstance().defaultProject
        descriptor = FishLspServerDescriptor(project)
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(FishLspSettings.Companion)
    }

    @Test
    fun `isSupportedFile returns true for fish files`() {
        val fishFile = mockk<VirtualFile>()
        every { fishFile.fileType } returns FishFileType.INSTANCE

        assertTrue(descriptor.isSupportedFile(fishFile))
    }

    @Test
    fun `isSupportedFile returns false for non-fish files`() {
        val otherFile = mockk<VirtualFile>()
        every { otherFile.fileType } returns mockk()

        assertFalse(descriptor.isSupportedFile(otherFile))
    }

    @Test
    fun `createCommandLine uses effective path from settings`() {
        val commandLine = descriptor.createCommandLine()

        assertNotNull(commandLine)
        assertTrue(commandLine.parametersList.list.contains("start"))
        assertTrue(commandLine.parametersList.list.contains("--stdio"))
    }
}
