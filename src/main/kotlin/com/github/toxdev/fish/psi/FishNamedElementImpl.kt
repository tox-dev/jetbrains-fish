package com.github.toxdev.fish.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

abstract class FishNamedElementImpl(
    node: ASTNode,
) : ASTWrapperPsiElement(node),
    FishNamedElement {
    override fun getName(): String? = nameIdentifier?.text

    override fun setName(name: String): PsiElement {
        val identifier = nameIdentifier
        if (identifier != null) {
            val newElement = FishElementFactory.createFunctionName(project, name)
            identifier.replace(newElement)
        }
        return this
    }

    override fun getNameIdentifier(): PsiElement? {
        val types = FishTypes::class.java
        val wordField = types.getDeclaredField("WORD")
        val wordType = wordField.get(null) as com.intellij.psi.tree.IElementType
        findChildByType<PsiElement>(wordType)?.let { return it }
        val variableField = types.getDeclaredField("VARIABLE")
        val variableType = variableField.get(null) as com.intellij.psi.tree.IElementType
        return findChildByType<PsiElement>(variableType)
    }

    override fun getTextOffset(): Int = nameIdentifier?.textOffset ?: super.getTextOffset()
}
