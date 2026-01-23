package com.github.toxdev.fish

import com.intellij.lang.Language

/**
 * The Fish shell language definition.
 */
class FishLanguage private constructor() : Language("Fish") {
    companion object {
        @JvmField
        val INSTANCE = FishLanguage()
    }

    override fun getDisplayName(): String = "Fish"
}
