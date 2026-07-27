package com.github.toxdev.fish.structure

import com.github.toxdev.fish.FishFileType
import com.github.toxdev.fish.psi.FishFile
import com.github.toxdev.fish.psi.FishFunctionBlock
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.ProjectManager
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.junit5.TestApplication
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@TestApplication
class FishStructureViewElementTest {
    private val project get() = ProjectManager.getInstance().defaultProject

    private fun <T> read(block: () -> T): T = ApplicationManager.getApplication().runReadAction<T>(block)

    private fun createFile(
        name: String,
        content: String,
    ): FishFile = read { PsiFileFactory.getInstance(project).createFileFromText(name, FishFileType.INSTANCE, content) as FishFile }

    private fun firstFunction(content: String): FishFunctionBlock =
        read { PsiTreeUtil.findChildrenOfType(createFile("test.fish", content), FishFunctionBlock::class.java).first() }

    @Test
    fun `getValue returns element`() {
        val element = mockk<PsiElement>(relaxed = true)
        val viewElement = FishStructureViewElement(element)
        assertSame(element, viewElement.value)
    }

    @Test
    fun `navigate does not throw for NavigatablePsiElement`() {
        val element = mockk<NavigatablePsiElement>(relaxed = true)
        val viewElement = FishStructureViewElement(element)
        viewElement.navigate(true)
    }

    @Test
    fun `navigate does nothing for non-NavigatablePsiElement`() {
        val element = mockk<PsiElement>(relaxed = true)
        val viewElement = FishStructureViewElement(element)
        viewElement.navigate(true)
    }

    @Test
    fun `canNavigate returns true when element can navigate`() {
        val element = mockk<NavigatablePsiElement>(relaxed = true)
        every { element.canNavigate() } returns true
        val viewElement = FishStructureViewElement(element)
        assertTrue(viewElement.canNavigate())
    }

    @Test
    fun `canNavigate returns false for non-NavigatablePsiElement`() {
        val element = mockk<PsiElement>(relaxed = true)
        val viewElement = FishStructureViewElement(element)
        assertFalse(viewElement.canNavigate())
    }

    @Test
    fun `canNavigateToSource returns true when element can navigate to source`() {
        val element = mockk<NavigatablePsiElement>(relaxed = true)
        every { element.canNavigateToSource() } returns true
        val viewElement = FishStructureViewElement(element)
        assertTrue(viewElement.canNavigateToSource())
    }

    @Test
    fun `canNavigateToSource returns false for non-NavigatablePsiElement`() {
        val element = mockk<PsiElement>(relaxed = true)
        val viewElement = FishStructureViewElement(element)
        assertFalse(viewElement.canNavigateToSource())
    }

    @Test
    fun `getAlphaSortKey returns function name for FishFunctionBlock`() {
        val viewElement = FishStructureViewElement(firstFunction("function my_function\nend"))
        assertEquals("my_function", read { viewElement.alphaSortKey })
    }

    @Test
    fun `getAlphaSortKey returns empty for other elements`() {
        val element = mockk<PsiElement>(relaxed = true)
        val viewElement = FishStructureViewElement(element)
        assertEquals("", viewElement.alphaSortKey)
    }

    @Test
    fun `getPresentation returns presentation`() {
        val element = mockk<PsiElement>(relaxed = true)
        val viewElement = FishStructureViewElement(element)
        assertNotNull(viewElement.presentation)
    }

    @Test
    fun `presentation getPresentableText returns function name for FishFunctionBlock`() {
        val viewElement = FishStructureViewElement(firstFunction("function test_func\nend"))
        assertEquals("test_func", read { viewElement.presentation.presentableText })
    }

    @Test
    fun `presentation getPresentableText returns file name for FishFile`() {
        val viewElement = FishStructureViewElement(createFile("script.fish", ""))
        assertEquals("script.fish", read { viewElement.presentation.presentableText })
    }

    @Test
    fun `presentation getPresentableText returns null for other elements`() {
        val element = mockk<PsiElement>(relaxed = true)
        val viewElement = FishStructureViewElement(element)
        assertNull(viewElement.presentation.presentableText)
    }

    @Test
    fun `presentation getIcon returns icon for FishFunctionBlock`() {
        val viewElement = FishStructureViewElement(firstFunction("function foo\nend"))
        assertNotNull(read { viewElement.presentation.getIcon(false) })
    }

    @Test
    fun `presentation getIcon returns icon for FishFile`() {
        val viewElement = FishStructureViewElement(createFile("script.fish", ""))
        assertNotNull(read { viewElement.presentation.getIcon(false) })
    }

    @Test
    fun `presentation getIcon returns null for other elements`() {
        val element = mockk<PsiElement>(relaxed = true)
        val viewElement = FishStructureViewElement(element)
        assertNull(viewElement.presentation.getIcon(false))
    }

    @Test
    fun `getChildren returns empty for non-FishFile`() {
        val element = mockk<PsiElement>(relaxed = true)
        val viewElement = FishStructureViewElement(element)
        assertEquals(0, read { viewElement.children.size })
    }

    @Test
    fun `getChildren returns empty for FishFile with no functions`() {
        val viewElement = FishStructureViewElement(createFile("script.fish", "echo hello\n"))
        assertEquals(0, read { viewElement.children.size })
    }

    @Test
    fun `getChildren returns function elements for FishFile with functions`() {
        val viewElement = FishStructureViewElement(createFile("script.fish", "function a\nend\nfunction b\nend\n"))
        assertEquals(2, read { viewElement.children.size })
    }
}
