package com.github.toxdev.fish.run

import com.github.toxdev.fish.psi.FishFile
import com.intellij.execution.lineMarker.RunLineMarkerContributor
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import java.util.function.Function
import javax.swing.Icon

class FishRunLineMarkerProvider : RunLineMarkerContributor() {
    override fun getInfo(element: PsiElement): Info? {
        if (element.firstChild != null) return null
        if (!isFirstLeafInFile(element)) return null
        val file = element.containingFile as? FishFile ?: return null
        val virtualFile = file.virtualFile ?: return null
        if (virtualFile.extension != "fish") return null
        val action = ActionManager.getInstance().getAction(FishRunFileAction.ID) ?: return null
        return FishRunInfo(
            AllIcons.RunConfigurations.TestState.Run,
            arrayOf(action),
            { "Run ${virtualFile.name}" },
        )
    }

    private class FishRunInfo(
        icon: Icon,
        actions: Array<AnAction>,
        tooltipProvider: Function<in PsiElement, String>,
    ) : Info(icon, actions, tooltipProvider) {
        override fun shouldReplace(other: Info): Boolean = true
    }

    private fun isFirstLeafInFile(element: PsiElement): Boolean {
        val file = element.containingFile as? PsiFile ?: return false
        val firstLeaf = findFirstLeaf(file) ?: return false
        return element == firstLeaf
    }

    private fun findFirstLeaf(element: PsiElement): PsiElement? {
        var current: PsiElement = element
        while (current.firstChild != null) {
            current = current.firstChild
        }
        return current
    }
}
