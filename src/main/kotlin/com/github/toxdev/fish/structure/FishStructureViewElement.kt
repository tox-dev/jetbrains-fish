package com.github.toxdev.fish.structure

import com.github.toxdev.fish.FishIcons
import com.github.toxdev.fish.psi.FishFile
import com.github.toxdev.fish.psi.FishFunctionBlock
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import javax.swing.Icon

class FishStructureViewElement(
    private val element: PsiElement,
) : StructureViewTreeElement,
    SortableTreeElement {
    override fun getValue(): Any = element

    override fun navigate(requestFocus: Boolean) {
        if (element is NavigatablePsiElement) {
            element.navigate(requestFocus)
        }
    }

    override fun canNavigate(): Boolean = element is NavigatablePsiElement && element.canNavigate()

    override fun canNavigateToSource(): Boolean = element is NavigatablePsiElement && element.canNavigateToSource()

    override fun getAlphaSortKey(): String =
        when (element) {
            is FishFunctionBlock -> element.functionName?.text ?: ""
            else -> ""
        }

    override fun getPresentation(): ItemPresentation =
        object : ItemPresentation {
            override fun getPresentableText(): String? =
                when (element) {
                    is FishFunctionBlock -> element.functionName?.text
                    is FishFile -> element.name
                    else -> null
                }

            override fun getIcon(unused: Boolean): Icon? =
                when (element) {
                    is FishFunctionBlock -> FishIcons.FILE
                    is FishFile -> FishIcons.FILE
                    else -> null
                }
        }

    override fun getChildren(): Array<TreeElement> {
        if (element is FishFile) {
            val functions = PsiTreeUtil.findChildrenOfType(element, FishFunctionBlock::class.java)
            return functions.map { FishStructureViewElement(it) }.toTypedArray()
        }
        return emptyArray()
    }
}
