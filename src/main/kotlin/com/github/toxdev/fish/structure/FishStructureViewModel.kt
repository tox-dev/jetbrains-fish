package com.github.toxdev.fish.structure

import com.github.toxdev.fish.psi.FishFunctionBlock
import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.Sorter
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile

class FishStructureViewModel(
    psiFile: PsiFile,
    editor: Editor?,
) : StructureViewModelBase(psiFile, editor, FishStructureViewElement(psiFile)),
    StructureViewModel.ElementInfoProvider {
    init {
        withSuitableClasses(FishFunctionBlock::class.java)
    }

    override fun getSorters(): Array<Sorter> = arrayOf(Sorter.ALPHA_SORTER)

    override fun isAlwaysShowsPlus(element: StructureViewTreeElement): Boolean = false

    override fun isAlwaysLeaf(element: StructureViewTreeElement): Boolean = element.value is FishFunctionBlock
}
