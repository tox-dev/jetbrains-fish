package com.github.toxdev.fish.findUsages

import com.github.toxdev.fish.psi.FishFunctionBlock
import com.github.toxdev.fish.psi.FishNamedElement
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement

class FishFindUsagesProvider : FindUsagesProvider {
    override fun getWordsScanner(): WordsScanner? = null

    override fun canFindUsagesFor(psi: PsiElement): Boolean = psi is FishFunctionBlock || psi is FishNamedElement

    override fun getHelpId(psiElement: PsiElement): String? = null

    override fun getType(element: PsiElement): String =
        when (element) {
            is FishFunctionBlock -> "function"
            else -> ""
        }

    override fun getDescriptiveName(element: PsiElement): String =
        when (element) {
            is PsiNamedElement -> element.name ?: "unknown"
            else -> ""
        }

    override fun getNodeText(
        element: PsiElement,
        useFullName: Boolean,
    ): String = getDescriptiveName(element)
}
