package com.github.toxdev.fish.lsp4ij

import com.github.toxdev.fish.lsp.FishLspSettings
import com.intellij.execution.configurations.GeneralCommandLine
import com.redhat.devtools.lsp4ij.server.OSProcessStreamConnectionProvider

class FishLanguageServer : OSProcessStreamConnectionProvider() {
    init {
        val fishLspPath = FishLspSettings.getInstance().getEffectivePath()
        commandLine =
            GeneralCommandLine(fishLspPath, "start", "--stdio")
                .withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
    }
}
