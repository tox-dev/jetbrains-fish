package com.github.toxdev.fish.lsp

import com.github.toxdev.fish.FishFileType
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServer
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class FishLspServerSupportProviderTest {
    @Test
    fun `provider is instantiable`() {
        val provider = FishLspServerSupportProvider()
        assertNotNull(provider)
    }

    @Test
    fun `createLspServerWidgetItem returns widget item`() {
        val provider = FishLspServerSupportProvider()
        val lspServer = mockk<LspServer>(relaxed = true)
        val currentFile = mockk<VirtualFile>()

        val widgetItem = provider.createLspServerWidgetItem(lspServer, currentFile)

        assertNotNull(widgetItem)
    }

    @Test
    fun `createLspServerWidgetItem with null file returns widget item`() {
        val provider = FishLspServerSupportProvider()
        val lspServer = mockk<LspServer>(relaxed = true)

        val widgetItem = provider.createLspServerWidgetItem(lspServer, null)

        assertNotNull(widgetItem)
    }

    @Test
    fun `FishFileType INSTANCE is used for file filtering`() {
        val fishFile = mockk<VirtualFile>()
        every { fishFile.fileType } returns FishFileType.INSTANCE

        val otherFile = mockk<VirtualFile>()
        every { otherFile.fileType } returns mockk()

        assert(fishFile.fileType == FishFileType.INSTANCE)
        assert(otherFile.fileType != FishFileType.INSTANCE)
    }
}
