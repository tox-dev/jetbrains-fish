package com.github.toxdev.fish

import com.github.toxdev.fish.psi.FishBeginBlock
import com.github.toxdev.fish.psi.FishForBlock
import com.github.toxdev.fish.psi.FishFunctionBlock
import com.github.toxdev.fish.psi.FishIfBlock
import com.github.toxdev.fish.psi.FishSwitchBlock
import com.github.toxdev.fish.psi.FishWhileBlock
import com.intellij.lang.Language
import com.intellij.psi.PsiElement
import com.intellij.ui.breadcrumbs.BreadcrumbsProvider

class FishBreadcrumbsProvider : BreadcrumbsProvider {
    override fun getLanguages(): Array<Language> = arrayOf(FishLanguage.INSTANCE)

    override fun acceptElement(element: PsiElement): Boolean =
        element is FishFunctionBlock ||
            element is FishIfBlock ||
            element is FishForBlock ||
            element is FishWhileBlock ||
            element is FishSwitchBlock ||
            element is FishBeginBlock

    override fun getElementInfo(element: PsiElement): String =
        when (element) {
            is FishFunctionBlock -> "function ${element.functionName?.text ?: "?"}"
            is FishIfBlock -> "if"
            is FishForBlock -> "for ${element.loopVariable?.text ?: "?"}"
            is FishWhileBlock -> "while"
            is FishSwitchBlock -> "switch"
            is FishBeginBlock -> "begin"
            else -> "?"
        }

    override fun getElementTooltip(element: PsiElement): String? = null
}
