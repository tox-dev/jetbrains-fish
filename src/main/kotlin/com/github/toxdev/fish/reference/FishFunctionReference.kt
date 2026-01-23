package com.github.toxdev.fish.reference

import com.github.toxdev.fish.psi.FishFile
import com.github.toxdev.fish.psi.FishFunctionBlock
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.util.PsiTreeUtil

class FishFunctionReference(
    element: PsiElement,
) : PsiReferenceBase<PsiElement>(element, TextRange(0, element.textLength)) {
    override fun resolve(): PsiElement? {
        val functionName = element.text
        val file = element.containingFile as? FishFile ?: return null

        val functions = PsiTreeUtil.findChildrenOfType(file, FishFunctionBlock::class.java)
        return functions.firstOrNull { it.functionName?.text == functionName }?.functionName
    }

    override fun getVariants(): Array<Any> = emptyArray()
}
