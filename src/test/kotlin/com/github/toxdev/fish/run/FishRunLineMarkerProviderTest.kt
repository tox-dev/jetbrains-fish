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
    fun `getInfo returns null for non-leaf element`() {
        val element = mockk<PsiElement>()
        val child = mockk<PsiElement>()
        every { element.firstChild } returns child

        val info = provider.getInfo(element)

        assertNull(info)
    }

    @Test
    fun `getInfo returns null for element not in fish file`() {
        val element = mockk<PsiElement>()
        every { element.firstChild } returns null
        every { element.containingFile } returns null

        val info = provider.getInfo(element)

        assertNull(info)
    }

    @Test
    fun `getInfo returns null when element is not first leaf in file`() {
        val element = mockk<PsiElement>()
        val file = mockk<com.github.toxdev.fish.psi.FishFile>()
        val firstChild = mockk<PsiElement>()
        val firstLeaf = mockk<PsiElement>()

        every { element.firstChild } returns null
        every { element.containingFile } returns file
        every { file.firstChild } returns firstChild
        every { firstChild.firstChild } returns firstLeaf
        every { firstLeaf.firstChild } returns null

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

        every { element.firstChild } returns null
        every { element.containingFile } returns file
        every { file.firstChild } returns null

        val info = provider.getInfo(element)

        assertNull(info)
    }

    @Test
    fun `getInfo returns null when virtualFile is null`() {
        val element = mockk<PsiElement>()
        val file = mockk<com.github.toxdev.fish.psi.FishFile>()

        every { element.firstChild } returns null
        every { element.text } returns "echo"
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

        every { element.firstChild } returns null
        every { element.text } returns "echo"
        every { element.containingFile } returns file
        every { file.firstChild } returns element
        every { file.virtualFile } returns virtualFile
        every { virtualFile.extension } returns "txt"

        val info = provider.getInfo(element)

        assertNull(info)
    }
}
