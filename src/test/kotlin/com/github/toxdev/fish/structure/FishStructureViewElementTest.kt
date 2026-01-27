package com.github.toxdev.fish.structure

import com.github.toxdev.fish.psi.FishFile
import com.github.toxdev.fish.psi.FishFunctionBlock
import com.github.toxdev.fish.psi.FishFunctionName
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiElement
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FishStructureViewElementTest {
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
        val functionName = mockk<FishFunctionName>(relaxed = true)
        every { functionName.text } returns "my_function"
        val functionBlock = mockk<FishFunctionBlock>(relaxed = true)
        every { functionBlock.functionName } returns functionName
        val viewElement = FishStructureViewElement(functionBlock)
        assertEquals("my_function", viewElement.alphaSortKey)
    }

    @Test
    fun `getAlphaSortKey returns empty for FishFunctionBlock without name`() {
        val functionBlock = mockk<FishFunctionBlock>(relaxed = true)
        every { functionBlock.functionName } returns null
        val viewElement = FishStructureViewElement(functionBlock)
        assertEquals("", viewElement.alphaSortKey)
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
        val functionName = mockk<FishFunctionName>(relaxed = true)
        every { functionName.text } returns "test_func"
        val functionBlock = mockk<FishFunctionBlock>(relaxed = true)
        every { functionBlock.functionName } returns functionName
        val viewElement = FishStructureViewElement(functionBlock)
        assertEquals("test_func", viewElement.presentation.presentableText)
    }

    @Test
    fun `presentation getPresentableText returns file name for FishFile`() {
        val fishFile = mockk<FishFile>(relaxed = true)
        every { fishFile.name } returns "script.fish"
        val viewElement = FishStructureViewElement(fishFile)
        assertEquals("script.fish", viewElement.presentation.presentableText)
    }

    @Test
    fun `presentation getPresentableText returns null for other elements`() {
        val element = mockk<PsiElement>(relaxed = true)
        val viewElement = FishStructureViewElement(element)
        assertNull(viewElement.presentation.presentableText)
    }

    @Test
    fun `presentation getIcon returns icon for FishFunctionBlock`() {
        val functionBlock = mockk<FishFunctionBlock>(relaxed = true)
        val viewElement = FishStructureViewElement(functionBlock)
        assertNotNull(viewElement.presentation.getIcon(false))
    }

    @Test
    fun `presentation getIcon returns icon for FishFile`() {
        val fishFile = mockk<FishFile>(relaxed = true)
        val viewElement = FishStructureViewElement(fishFile)
        assertNotNull(viewElement.presentation.getIcon(false))
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
        assertEquals(0, viewElement.children.size)
    }

    @Test
    fun `getChildren returns function elements for FishFile with no functions`() {
        val fishFile = mockk<FishFile>(relaxed = true)
        val viewElement = FishStructureViewElement(fishFile)
        val children = viewElement.children
        assertNotNull(children)
    }
}
