package com.github.toxdev.fish.features

import com.github.toxdev.fish.FishBreadcrumbsProvider
import com.github.toxdev.fish.FishLanguage
import com.github.toxdev.fish.completion.FishBuiltinCompletionProvider
import com.github.toxdev.fish.completion.FishKeywordCompletionProvider
import com.github.toxdev.fish.findUsages.FishFindUsagesProvider
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BasicFeatureTest {
    @Test
    fun `breadcrumbs provider supports fish language`() {
        val provider = FishBreadcrumbsProvider()
        val languages = provider.languages

        assertTrue(languages.contains(FishLanguage.INSTANCE))
        assertNotNull(languages)
    }

    @Test
    fun `breadcrumbs provider getElementTooltip returns null`() {
        val provider = FishBreadcrumbsProvider()
        val element = mockk<com.intellij.psi.PsiElement>()

        assertEquals(null, provider.getElementTooltip(element))
    }

    @Test
    fun `find usages provider getWordsScanner returns null`() {
        val provider = FishFindUsagesProvider()

        assertEquals(null, provider.getWordsScanner())
    }

    @Test
    fun `find usages provider getHelpId returns null`() {
        val provider = FishFindUsagesProvider()
        val element = mockk<com.intellij.psi.PsiElement>()

        assertEquals(null, provider.getHelpId(element))
    }

    @Test
    fun `find usages provider getType returns empty for unknown element`() {
        val provider = FishFindUsagesProvider()
        val element = mockk<com.intellij.psi.PsiElement>()

        assertEquals("", provider.getType(element))
    }

    @Test
    fun `find usages provider getDescriptiveName returns empty for unknown element`() {
        val provider = FishFindUsagesProvider()
        val element = mockk<com.intellij.psi.PsiElement>()

        assertEquals("", provider.getDescriptiveName(element))
    }

    @Test
    fun `find usages provider getNodeText returns empty for unknown element`() {
        val provider = FishFindUsagesProvider()
        val element = mockk<com.intellij.psi.PsiElement>()

        assertEquals("", provider.getNodeText(element, false))
        assertEquals("", provider.getNodeText(element, true))
    }

    @Test
    fun `find usages provider canFindUsagesFor returns false for unknown element`() {
        val provider = FishFindUsagesProvider()
        val element = mockk<com.intellij.psi.PsiElement>()

        assertFalse(provider.canFindUsagesFor(element))
    }

    @Test
    fun `keyword completion provider is instantiable`() {
        val provider = FishKeywordCompletionProvider()

        assertNotNull(provider)
    }

    @Test
    fun `builtin completion provider is instantiable`() {
        val provider = FishBuiltinCompletionProvider()

        assertNotNull(provider)
    }
}
