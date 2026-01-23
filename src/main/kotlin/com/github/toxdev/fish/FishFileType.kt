package com.github.toxdev.fish

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

/**
 * File type for Fish shell scripts (.fish files).
 */
class FishFileType private constructor() : LanguageFileType(FishLanguage.INSTANCE) {
    override fun getName(): String = "Fish"

    override fun getDescription(): String = "Fish shell script"

    override fun getDefaultExtension(): String = "fish"

    override fun getIcon(): Icon = FishIcons.FILE

    companion object {
        @JvmField
        val INSTANCE = FishFileType()
    }
}
