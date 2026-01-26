package com.github.toxdev.fish.formatting

import com.github.toxdev.fish.FishFileType
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.psi.PsiFile
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class FishExternalFormatterTest {
    private val formatter = FishExternalFormatter()

    @BeforeEach
    fun setUp() {
        FishExternalFormatter.resetCache()
    }

    @AfterEach
    fun tearDown() {
        FishExternalFormatter.resetCache()
    }

    @Test
    fun `getFeatures returns empty set`() {
        val features = formatter.features

        assertTrue(features.isEmpty())
    }

    @Test
    fun `canFormat returns false for non-Fish files`() {
        val file = mockk<PsiFile>()
        every { file.fileType } returns PlainTextFileType.INSTANCE

        assertFalse(formatter.canFormat(file))
    }

    @Test
    fun `canFormat returns false when fish_indent not found`() {
        mockkObject(FishExternalFormatter.Companion)
        every { FishExternalFormatter.findFishIndent() } returns null

        val file = mockk<PsiFile>()
        every { file.fileType } returns FishFileType.INSTANCE

        assertFalse(formatter.canFormat(file))

        unmockkObject(FishExternalFormatter.Companion)
    }

    @Test
    fun `canFormat returns true for Fish files when fish_indent available`() {
        mockkObject(FishExternalFormatter.Companion)
        every { FishExternalFormatter.findFishIndent() } returns "/usr/bin/fish_indent"

        val file = mockk<PsiFile>()
        every { file.fileType } returns FishFileType.INSTANCE

        assertTrue(formatter.canFormat(file))

        unmockkObject(FishExternalFormatter.Companion)
    }

    @Test
    fun `findFishIndent caches result`() {
        FishExternalFormatter.resetCache()
        val result1 = FishExternalFormatter.findFishIndent()
        val result2 = FishExternalFormatter.findFishIndent()

        assertEquals(result1, result2)
    }

    @Test
    fun `runFishIndent returns null when process does not read stdin`() {
        val content = "echo hello"
        val tempFile = File.createTempFile("test", ".fish")
        tempFile.deleteOnExit()
        val result = FishExternalFormatter.runFishIndent("/bin/sleep", content, tempFile)

        assertNull(result)
    }

    @Test
    fun `runFishIndent returns null when executable not found`() {
        val content = "echo hello"
        val tempFile = File.createTempFile("test", ".fish")
        tempFile.deleteOnExit()

        assertNull(FishExternalFormatter.runFishIndent("/nonexistent/path/to/fish_indent", content, tempFile))
    }

    @Test
    fun `runFishIndent works with null working directory`() {
        val content = "echo hello"
        val result = FishExternalFormatter.runFishIndent("/bin/cat", content, null)

        assertEquals("echo hello", result)
    }

    @Test
    fun `runFishIndent works with valid working directory`() {
        val content = "test content"
        val tempFile = File.createTempFile("test", ".fish")
        tempFile.deleteOnExit()
        val result = FishExternalFormatter.runFishIndent("/bin/cat", content, tempFile)

        assertEquals("test content", result)
    }

    @Test
    fun `runFishIndent handles non-existent parent directory`() {
        val content = "echo hello"
        val fakeFile = File("/nonexistent/path/file.fish")
        val result = FishExternalFormatter.runFishIndent("/bin/cat", content, fakeFile)

        assertEquals("echo hello", result)
    }
}
