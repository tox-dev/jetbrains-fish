package com.github.toxdev.fish.lsp

import com.github.toxdev.fish.FishFileType
import com.github.toxdev.fish.FishIcons
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspClient
import com.intellij.platform.lsp.api.LspIntegrationProvider
import com.intellij.platform.lsp.api.LspIntegrationProvider.LspClientStarter
import com.intellij.platform.lsp.api.lsWidget.LspClientWidgetItem

private val LOG = logger<FishLspIntegrationProvider>()

class FishLspIntegrationProvider : LspIntegrationProvider {
    override fun fileOpened(
        project: Project,
        file: VirtualFile,
        clientStarter: LspClientStarter,
    ) {
        if (file.fileType != FishFileType.INSTANCE) return

        if (!FishLspSettings.isFishLspAvailable()) {
            LOG.warn("fish-lsp not found, showing notification")
            FishLspNotification.notifyFishLspNotFound(project)
            return
        }

        LOG.info("Starting Fish LSP server for ${file.name}")
        clientStarter.ensureClientStarted(FishLspClientDescriptor(project))
    }

    override fun createWidgetItem(
        lspClient: LspClient,
        currentFile: VirtualFile?,
    ): LspClientWidgetItem = LspClientWidgetItem(lspClient, currentFile, FishIcons.FILE, FishLspConfigurable::class.java)
}
