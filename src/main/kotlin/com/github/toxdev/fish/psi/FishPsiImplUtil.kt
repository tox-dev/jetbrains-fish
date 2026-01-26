package com.github.toxdev.fish.psi

import com.intellij.psi.PsiElement

object FishPsiImplUtil {
    @JvmStatic
    fun getName(element: FishFunctionBlock): String? = element.functionName?.name

    @JvmStatic
    fun setName(
        element: FishFunctionBlock,
        name: String,
    ): PsiElement {
        element.functionName?.setName(name)
        return element
    }

    @JvmStatic
    fun getNameIdentifier(element: FishFunctionBlock): PsiElement? = element.functionName?.nameIdentifier
}
