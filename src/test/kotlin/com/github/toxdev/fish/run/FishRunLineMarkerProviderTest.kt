package com.github.toxdev.fish.run

import com.intellij.psi.PsiElement
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class FishRunLineMarkerProviderTest {
    private val provider = FishRunLineMarkerProvider()

    @Test
    fun `getInfo returns null for element not in fish file`() {
        val element = mockk<PsiElement>()
        every { element.containingFile } returns null

        val info = provider.getInfo(element)

        assertNull(info)
    }

    @Test
    fun `getInfo returns null when element is not first in file`() {
        val element = mockk<PsiElement>()
        val file = mockk<com.github.toxdev.fish.psi.FishFile>()
        val firstChild = mockk<PsiElement>()

        every { element.containingFile } returns file
        every { file.firstChild } returns firstChild
        every { firstChild.text } returns "some text"
        every { firstChild.nextSibling } returns null

        val info = provider.getInfo(element)

        assertNull(info)
    }

    @Test
    fun `provider is instantiable`() {
        assertNotNull(provider)
    }
}
