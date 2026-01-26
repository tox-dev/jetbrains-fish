package com.github.toxdev.fish.psi

import com.github.toxdev.fish.FishLanguage
import com.intellij.psi.tree.IElementType

/**
 * Custom element type for Fish shell language PSI elements.
 */
class FishElementType(
    debugName: String,
) : IElementType(debugName, FishLanguage.INSTANCE) {
    override fun toString(): String = "FishElementType.${super.toString()}"
}
