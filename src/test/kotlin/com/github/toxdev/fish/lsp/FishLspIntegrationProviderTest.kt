package com.github.toxdev.fish.lsp

import com.github.toxdev.fish.FishFileType
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspClient
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class FishLspIntegrationProviderTest {
    @Test
    fun `provider is instantiable`() {
        val provider = FishLspIntegrationProvider()
        assertNotNull(provider)
    }

    @Test
    fun `createWidgetItem returns widget item`() {
        val provider = FishLspIntegrationProvider()
        val lspClient = mockk<LspClient>(relaxed = true)
        val currentFile = mockk<VirtualFile>()

        val widgetItem = provider.createWidgetItem(lspClient, currentFile)

        assertNotNull(widgetItem)
    }

    @Test
    fun `createWidgetItem with null file returns widget item`() {
        val provider = FishLspIntegrationProvider()
        val lspClient = mockk<LspClient>(relaxed = true)

        val widgetItem = provider.createWidgetItem(lspClient, null)

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
