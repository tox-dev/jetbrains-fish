package com.github.toxdev.fish.psi

import com.github.toxdev.fish.FishLanguage
import com.intellij.psi.tree.IElementType

/**
 * Custom token type for Fish shell language.
 */
class FishTokenType(
    debugName: String,
) : IElementType(debugName, FishLanguage.INSTANCE) {
    override fun toString(): String = "FishTokenType.${super.toString()}"
}
