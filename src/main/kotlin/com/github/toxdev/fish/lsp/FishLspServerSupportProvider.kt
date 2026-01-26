package com.github.toxdev.fish.lsp

import com.github.toxdev.fish.FishFileType
import com.github.toxdev.fish.FishIcons
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServer
import com.intellij.platform.lsp.api.LspServerSupportProvider
import com.intellij.platform.lsp.api.LspServerSupportProvider.LspServerStarter
import com.intellij.platform.lsp.api.lsWidget.LspServerWidgetItem

private val LOG = logger<FishLspServerSupportProvider>()

class FishLspServerSupportProvider : LspServerSupportProvider {
    override fun fileOpened(
        project: Project,
        file: VirtualFile,
        serverStarter: LspServerStarter,
    ) {
        if (file.fileType != FishFileType.INSTANCE) return

        if (!FishLspSettings.isFishLspAvailable()) {
            LOG.warn("fish-lsp not found, showing notification")
            FishLspNotification.notifyFishLspNotFound(project)
            return
        }

        LOG.info("Starting Fish LSP server for ${file.name}")
        serverStarter.ensureServerStarted(FishLspServerDescriptor(project))
    }

    override fun createLspServerWidgetItem(
        lspServer: LspServer,
        currentFile: VirtualFile?,
    ): LspServerWidgetItem = LspServerWidgetItem(lspServer, currentFile, FishIcons.FILE, FishLspConfigurable::class.java)
}
