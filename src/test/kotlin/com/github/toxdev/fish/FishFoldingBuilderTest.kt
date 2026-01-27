package com.github.toxdev.fish

import com.github.toxdev.fish.psi.FishFile
import com.intellij.lang.ASTNode
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.ProjectManager
import com.intellij.psi.PsiFileFactory
import com.intellij.testFramework.junit5.TestApplication
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FishFoldingBuilderTest {
    private val builder = FishFoldingBuilder()

    @Test
    fun `getPlaceholderText returns ellipsis`() {
        assertEquals("...", builder.getPlaceholderText(mockk<ASTNode>()))
    }

    @Test
    fun `isCollapsedByDefault returns false`() {
        assertFalse(builder.isCollapsedByDefault(mockk<ASTNode>()))
    }
}

@TestApplication
class FishFoldingBuilderPlatformTest {
    private val builder = FishFoldingBuilder()
    private val project get() = ProjectManager.getInstance().defaultProject
    private val mockDocument = mockk<Document>(relaxed = true)

    private fun createPsiFile(content: String): FishFile {
        val factory = PsiFileFactory.getInstance(project)
        return factory.createFileFromText("test.fish", FishFileType.INSTANCE, content) as FishFile
    }

    @Test
    fun `buildFoldRegions returns empty for empty file`() {
        val content = ""
        val file = ApplicationManager.getApplication().runReadAction<FishFile> { createPsiFile(content) }

        val regions =
            ApplicationManager.getApplication().runReadAction<Array<*>> {
                builder.buildFoldRegions(file, mockDocument, false)
            }

        assertEquals(0, regions.size)
    }

    @Test
    fun `buildFoldRegions finds function blocks`() {
        val content =
            """
            function my_func
                echo "hello world"
            end
            """.trimIndent()
        val file = ApplicationManager.getApplication().runReadAction<FishFile> { createPsiFile(content) }

        val regions =
            ApplicationManager.getApplication().runReadAction<Array<*>> {
                builder.buildFoldRegions(file, mockDocument, false)
            }

        assertTrue(regions.isNotEmpty())
    }

    @Test
    fun `buildFoldRegions finds if blocks`() {
        val content =
            """
            if test -f somefile
                echo "file exists"
            end
            """.trimIndent()
        val file = ApplicationManager.getApplication().runReadAction<FishFile> { createPsiFile(content) }

        val regions =
            ApplicationManager.getApplication().runReadAction<Array<*>> {
                builder.buildFoldRegions(file, mockDocument, false)
            }

        assertTrue(regions.isNotEmpty())
    }

    @Test
    fun `buildFoldRegions finds while blocks`() {
        val content =
            """
            while true
                echo "looping"
            end
            """.trimIndent()
        val file = ApplicationManager.getApplication().runReadAction<FishFile> { createPsiFile(content) }

        val regions =
            ApplicationManager.getApplication().runReadAction<Array<*>> {
                builder.buildFoldRegions(file, mockDocument, false)
            }

        assertTrue(regions.isNotEmpty())
    }

    @Test
    fun `buildFoldRegions finds for blocks`() {
        val content =
            """
            for item in a b c
                echo item
            end
            """.trimIndent()
        val file = ApplicationManager.getApplication().runReadAction<FishFile> { createPsiFile(content) }

        val regions =
            ApplicationManager.getApplication().runReadAction<Array<*>> {
                builder.buildFoldRegions(file, mockDocument, false)
            }

        assertTrue(regions.isNotEmpty())
    }

    @Test
    fun `buildFoldRegions finds switch blocks`() {
        val content =
            """
            switch value
                case a
                    echo "a"
            end
            """.trimIndent()
        val file = ApplicationManager.getApplication().runReadAction<FishFile> { createPsiFile(content) }

        val regions =
            ApplicationManager.getApplication().runReadAction<Array<*>> {
                builder.buildFoldRegions(file, mockDocument, false)
            }

        assertTrue(regions.isNotEmpty())
    }

    @Test
    fun `buildFoldRegions finds begin blocks`() {
        val content =
            """
            begin
                echo "in begin block"
            end
            """.trimIndent()
        val file = ApplicationManager.getApplication().runReadAction<FishFile> { createPsiFile(content) }

        val regions =
            ApplicationManager.getApplication().runReadAction<Array<*>> {
                builder.buildFoldRegions(file, mockDocument, false)
            }

        assertTrue(regions.isNotEmpty())
    }

    @Test
    fun `buildFoldRegions ignores short blocks`() {
        val content =
            """
            if true; end
            """.trimIndent()
        val file = ApplicationManager.getApplication().runReadAction<FishFile> { createPsiFile(content) }

        val regions =
            ApplicationManager.getApplication().runReadAction<Array<*>> {
                builder.buildFoldRegions(file, mockDocument, false)
            }

        assertEquals(0, regions.size)
    }

    @Test
    fun `FishFile getFileType returns FishFileType`() {
        val content = "echo hello"
        val file = ApplicationManager.getApplication().runReadAction<FishFile> { createPsiFile(content) }

        ApplicationManager.getApplication().runReadAction {
            assertEquals(FishFileType.INSTANCE, file.fileType)
        }
    }

    @Test
    fun `FishFile toString returns Fish File`() {
        val content = "echo hello"
        val file = ApplicationManager.getApplication().runReadAction<FishFile> { createPsiFile(content) }

        ApplicationManager.getApplication().runReadAction {
            assertEquals("Fish File", file.toString())
        }
    }
}
