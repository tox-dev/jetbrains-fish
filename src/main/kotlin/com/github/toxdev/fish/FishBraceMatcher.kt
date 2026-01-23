package com.github.toxdev.fish

import com.github.toxdev.fish.psi.FishTypes
import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType

class FishBraceMatcher : PairedBraceMatcher {
    override fun getPairs(): Array<BracePair> = PAIRS

    override fun isPairedBracesAllowedBeforeType(
        lbraceType: IElementType,
        contextType: IElementType?,
    ): Boolean = true

    override fun getCodeConstructStart(
        file: PsiFile?,
        openingBraceOffset: Int,
    ): Int = openingBraceOffset

    companion object {
        private val PAIRS =
            arrayOf(
                BracePair(FishTypes.LPAREN, FishTypes.RPAREN, false),
                BracePair(FishTypes.LBRACE, FishTypes.RBRACE, true),
                BracePair(FishTypes.LBRACKET, FishTypes.RBRACKET, false),
            )
    }
}
