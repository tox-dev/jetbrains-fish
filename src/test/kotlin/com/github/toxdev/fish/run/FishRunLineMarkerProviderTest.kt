package com.github.toxdev.fish.run

import com.github.toxdev.fish.FishFileType
import com.intellij.execution.lineMarker.RunLineMarkerContributor
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.junit5.TestApplication
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
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
        val file = mockk<PsiFile>()
        every { element.firstChild } returns null
        every { element.prevSibling } returns null
        every { element.parent } returns file
        every { element.containingFile } returns file

        val info = provider.getInfo(element)

        assertNull(info)
    }

    @Test
    fun `getInfo returns null when element is not first leaf in file`() {
        val element = mockk<PsiElement>()
        val previous = mockk<PsiElement>()

        every { element.firstChild } returns null
        every { element.prevSibling } returns previous
        every { previous.lastChild } returns null

        val info = provider.getInfo(element)

        assertNull(info)
    }

    @Test
    fun `provider is instantiable`() {
        assertNotNull(provider)
    }

    @Test
    fun `getInfo returns null when virtualFile is null`() {
        val element = mockk<PsiElement>()
        val file = mockk<com.github.toxdev.fish.psi.FishFile>()

        every { element.firstChild } returns null
        every { element.prevSibling } returns null
        every { element.parent } returns file
        every { element.containingFile } returns file
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
        every { element.prevSibling } returns null
        every { element.parent } returns file
        every { element.containingFile } returns file
        every { file.virtualFile } returns virtualFile
        every { virtualFile.extension } returns "txt"

        val info = provider.getInfo(element)

        assertNull(info)
    }
}

@TestApplication
class FishRunLineMarkerProviderPlatformTest {
    private val provider = FishRunLineMarkerProvider()

    private fun firstLeafInfo(): Pair<PsiElement, RunLineMarkerContributor.Info?> =
        ApplicationManager.getApplication().runReadAction<Pair<PsiElement, RunLineMarkerContributor.Info?>> {
            val file =
                PsiFileFactory
                    .getInstance(ProjectManager.getInstance().defaultProject)
                    .createFileFromText("test.fish", FishFileType.INSTANCE, "echo hello\n", 0, true)
            val leaf = PsiTreeUtil.getDeepestFirst(file)
            leaf to provider.getInfo(leaf)
        }

    @Test
    fun `getInfo offers the run action on the first leaf`() {
        val info = firstLeafInfo().second

        assertEquals(listOf(ActionManager.getInstance().getAction(FishRunFileAction.ID)), info?.actions?.toList())
    }

    @Test
    fun `getInfo tooltip names the file`() {
        val (leaf, info) = firstLeafInfo()

        assertEquals("Run test.fish", info?.tooltipProvider?.apply(leaf))
    }

    @Test
    fun `getInfo replaces other run markers`() {
        val info = firstLeafInfo().second

        assertTrue(info?.shouldReplace(mockk()) == true)
    }
}
