package com.github.toxdev.fish.lsp4ij

import com.github.toxdev.fish.lsp.FishLspNotification
import com.github.toxdev.fish.lsp.FishLspSettings
import com.intellij.openapi.project.Project
import com.redhat.devtools.lsp4ij.LanguageServerFactory
import com.redhat.devtools.lsp4ij.server.StreamConnectionProvider

class FishLanguageServerFactory : LanguageServerFactory {
    override fun createConnectionProvider(project: Project): StreamConnectionProvider {
        if (!FishLspSettings.isFishLspAvailable()) {
            FishLspNotification.notifyFishLspNotFound(project)
        }
        return FishLanguageServer()
    }
}
