package com.github.toxdev.fish

import com.github.toxdev.fish.psi.FishBeginBlock
import com.github.toxdev.fish.psi.FishForBlock
import com.github.toxdev.fish.psi.FishFunctionBlock
import com.github.toxdev.fish.psi.FishFunctionName
import com.github.toxdev.fish.psi.FishIfBlock
import com.github.toxdev.fish.psi.FishLoopVariable
import com.github.toxdev.fish.psi.FishSwitchBlock
import com.github.toxdev.fish.psi.FishWhileBlock
import com.intellij.psi.PsiElement
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FishBreadcrumbsProviderTest {
    private val provider = FishBreadcrumbsProvider()

    @Test
    fun `getLanguages returns FishLanguage`() {
        val languages = provider.languages
        assertEquals(1, languages.size)
        assertEquals(FishLanguage.INSTANCE, languages[0])
    }

    @Test
    fun `acceptElement returns true for FishFunctionBlock`() {
        assertTrue(provider.acceptElement(mockk<FishFunctionBlock>()))
    }

    @Test
    fun `acceptElement returns true for FishIfBlock`() {
        assertTrue(provider.acceptElement(mockk<FishIfBlock>()))
    }

    @Test
    fun `acceptElement returns true for FishForBlock`() {
        assertTrue(provider.acceptElement(mockk<FishForBlock>()))
    }

    @Test
    fun `acceptElement returns true for FishWhileBlock`() {
        assertTrue(provider.acceptElement(mockk<FishWhileBlock>()))
    }

    @Test
    fun `acceptElement returns true for FishSwitchBlock`() {
        assertTrue(provider.acceptElement(mockk<FishSwitchBlock>()))
    }

    @Test
    fun `acceptElement returns true for FishBeginBlock`() {
        assertTrue(provider.acceptElement(mockk<FishBeginBlock>()))
    }

    @Test
    fun `acceptElement returns false for other PsiElement`() {
        assertFalse(provider.acceptElement(mockk<PsiElement>()))
    }

    @Test
    fun `getElementInfo returns function with name for FishFunctionBlock`() {
        val functionName = mockk<FishFunctionName>()
        every { functionName.text } returns "my_func"
        val functionBlock = mockk<FishFunctionBlock>()
        every { functionBlock.functionName } returns functionName
        assertEquals("function my_func", provider.getElementInfo(functionBlock))
    }

    @Test
    fun `getElementInfo returns function with question mark when name is null`() {
        val functionBlock = mockk<FishFunctionBlock>()
        every { functionBlock.functionName } returns null
        assertEquals("function ?", provider.getElementInfo(functionBlock))
    }

    @Test
    fun `getElementInfo returns if for FishIfBlock`() {
        assertEquals("if", provider.getElementInfo(mockk<FishIfBlock>()))
    }

    @Test
    fun `getElementInfo returns for with variable for FishForBlock`() {
        val loopVariable = mockk<FishLoopVariable>()
        every { loopVariable.text } returns "i"
        val forBlock = mockk<FishForBlock>()
        every { forBlock.loopVariable } returns loopVariable
        assertEquals("for i", provider.getElementInfo(forBlock))
    }

    @Test
    fun `getElementInfo returns for with question mark when variable is null`() {
        val forBlock = mockk<FishForBlock>()
        every { forBlock.loopVariable } returns null
        assertEquals("for ?", provider.getElementInfo(forBlock))
    }

    @Test
    fun `getElementInfo returns while for FishWhileBlock`() {
        assertEquals("while", provider.getElementInfo(mockk<FishWhileBlock>()))
    }

    @Test
    fun `getElementInfo returns switch for FishSwitchBlock`() {
        assertEquals("switch", provider.getElementInfo(mockk<FishSwitchBlock>()))
    }

    @Test
    fun `getElementInfo returns begin for FishBeginBlock`() {
        assertEquals("begin", provider.getElementInfo(mockk<FishBeginBlock>()))
    }

    @Test
    fun `getElementInfo returns question mark for other element`() {
        assertEquals("?", provider.getElementInfo(mockk<PsiElement>()))
    }

    @Test
    fun `getElementTooltip returns null`() {
        assertNull(provider.getElementTooltip(mockk<PsiElement>()))
    }
}
