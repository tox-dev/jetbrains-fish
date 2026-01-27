package com.github.toxdev.fish.lsp

import com.github.toxdev.fish.FishFileType
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor

class FishLspServerDescriptor(
    project: Project,
) : ProjectWideLspServerDescriptor(project, "Fish LSP") {
    override fun isSupportedFile(file: VirtualFile): Boolean = file.fileType == FishFileType.INSTANCE

    override fun createCommandLine(): GeneralCommandLine {
        val fishLspPath = FishLspSettings.getInstance().getEffectivePath()
        return GeneralCommandLine(fishLspPath, "start", "--stdio")
            .withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
    }
}
