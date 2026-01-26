package com.github.toxdev.fish.features

import com.github.toxdev.fish.FishBreadcrumbsProvider
import com.github.toxdev.fish.FishLanguage
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
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
}
