package com.github.toxdev.fish.run

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
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

    @Test
    fun `getInfo returns null when file firstChild is null`() {
        val element = mockk<PsiElement>()
        val file = mockk<PsiFile>()

        every { element.containingFile } returns file
        every { file.firstChild } returns null

        val info = provider.getInfo(element)

        assertNull(info)
    }

    @Test
    fun `getInfo returns null when virtualFile is null`() {
        val element = mockk<PsiElement>()
        val file = mockk<com.github.toxdev.fish.psi.FishFile>()

        every { element.containingFile } returns file
        every { file.firstChild } returns element
        every { file.virtualFile } returns null

        val info = provider.getInfo(element)

        assertNull(info)
    }

    @Test
    fun `getInfo returns null when extension is not fish`() {
        val element = mockk<PsiElement>()
        val file = mockk<com.github.toxdev.fish.psi.FishFile>()
        val virtualFile = mockk<VirtualFile>()

        every { element.containingFile } returns file
        every { file.firstChild } returns element
        every { file.virtualFile } returns virtualFile
        every { virtualFile.extension } returns "txt"

        val info = provider.getInfo(element)

        assertNull(info)
    }

    @Test
    fun `getInfo returns null when element is not first child and skipWhitespaceAndComments returns null`() {
        val element = mockk<PsiElement>()
        val firstChild = mockk<PsiElement>()
        val file = mockk<com.github.toxdev.fish.psi.FishFile>()

        every { element.containingFile } returns file
        every { file.firstChild } returns firstChild
        every { firstChild.text } returns "something"
        every { firstChild.nextSibling } returns null

        val info = provider.getInfo(element)

        assertNull(info)
    }

    @Test
    fun `getInfo returns null when first child text starts with hash`() {
        val firstChild = mockk<PsiElement>()
        val secondChild = mockk<PsiElement>()
        val file = mockk<com.github.toxdev.fish.psi.FishFile>()

        every { firstChild.containingFile } returns file
        every { firstChild.text } returns "# comment"
        every { firstChild.nextSibling } returns secondChild

        every { secondChild.containingFile } returns file
        every { secondChild.text } returns "echo hello"
        every { secondChild.nextSibling } returns null

        every { file.firstChild } returns firstChild
        every { file.virtualFile } returns null

        val info = provider.getInfo(firstChild)

        assertNull(info)
    }
}
