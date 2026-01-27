package com.github.toxdev.fish

import com.github.toxdev.fish.psi.FishBeginBlock
import com.github.toxdev.fish.psi.FishForBlock
import com.github.toxdev.fish.psi.FishFunctionBlock
import com.github.toxdev.fish.psi.FishIfBlock
import com.github.toxdev.fish.psi.FishSwitchBlock
import com.github.toxdev.fish.psi.FishWhileBlock
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

class FishFoldingBuilder : FoldingBuilderEx() {
    override fun buildFoldRegions(
        root: PsiElement,
        document: Document,
        quick: Boolean,
    ): Array<FoldingDescriptor> {
        val descriptors = mutableListOf<FoldingDescriptor>()
        val foldableElements =
            PsiTreeUtil.findChildrenOfAnyType(
                root,
                FishFunctionBlock::class.java,
                FishIfBlock::class.java,
                FishWhileBlock::class.java,
                FishForBlock::class.java,
                FishSwitchBlock::class.java,
                FishBeginBlock::class.java,
            )

        for (element in foldableElements) {
            val range = element.textRange
            if (range.length > 20) {
                val placeholderText =
                    when (element) {
                        is FishFunctionBlock -> "function ${element.functionName?.text ?: "..."}..."
                        is FishIfBlock -> "if..."
                        is FishWhileBlock -> "while..."
                        is FishForBlock -> "for ${element.loopVariable?.text ?: "..."}..."
                        is FishSwitchBlock -> "switch..."
                        else -> "begin..."
                    }
                descriptors.add(FoldingDescriptor(element.node, range, null, placeholderText))
            }
        }

        return descriptors.toTypedArray()
    }

    override fun getPlaceholderText(node: ASTNode): String = "..."

    override fun isCollapsedByDefault(node: ASTNode): Boolean = false
}
