package com.github.toxdev.fish.run

import com.github.toxdev.fish.FishIcons
import com.github.toxdev.fish.psi.FishFile
import com.intellij.execution.lineMarker.ExecutorAction
import com.intellij.execution.lineMarker.RunLineMarkerContributor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

class FishRunLineMarkerProvider : RunLineMarkerContributor() {
    override fun getInfo(element: PsiElement): Info? {
        if (!isFirstElementInFile(element)) return null
        val file = element.containingFile as? FishFile ?: return null
        val virtualFile = file.virtualFile ?: return null
        if (virtualFile.extension != "fish") return null
        val actions = ExecutorAction.getActions(0)
        return Info(
            FishIcons.FILE,
            actions,
            { "Run ${virtualFile.name}" },
        )
    }

    private fun isFirstElementInFile(element: PsiElement): Boolean {
        val file = element.containingFile as? PsiFile ?: return false
        val firstChild = file.firstChild ?: return false
        return element == firstChild || element == skipWhitespaceAndComments(firstChild)
    }

    private fun skipWhitespaceAndComments(element: PsiElement): PsiElement? {
        var current: PsiElement? = element
        while (current != null) {
            val text = current.text
            if (text.isNotBlank() && !text.trim().startsWith("#")) {
                return current
            }
            current = current.nextSibling
        }
        return element
    }
}
