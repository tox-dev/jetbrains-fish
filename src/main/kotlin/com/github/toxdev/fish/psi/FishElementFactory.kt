package com.github.toxdev.fish.psi

import com.github.toxdev.fish.FishFileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory

object FishElementFactory {
    fun createFunctionName(
        project: Project,
        name: String,
    ): PsiElement {
        val file = createFile(project, "function $name\nend")
        val types = FishTypes::class.java
        val functionDeclarationType = types.getDeclaredField("FUNCTION_DECLARATION").get(null) as com.intellij.psi.tree.IElementType
        val functionDeclaration = file.node.findChildByType(functionDeclarationType)?.psi
        return functionDeclaration?.firstChild ?: throw IllegalStateException("Failed to create function name")
    }

    private fun createFile(
        project: Project,
        text: String,
    ): FishFile =
        PsiFileFactory
            .getInstance(project)
            .createFileFromText("dummy.fish", FishFileType.INSTANCE, text) as FishFile
}
