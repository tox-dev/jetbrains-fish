package com.github.toxdev.fish.psi

import com.github.toxdev.fish.FishFileType
import com.github.toxdev.fish.FishLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

/**
 * PSI file for Fish shell scripts.
 */
class FishFile(
    viewProvider: FileViewProvider,
) : PsiFileBase(viewProvider, FishLanguage.INSTANCE) {
    override fun getFileType(): FileType = FishFileType.INSTANCE

    override fun toString(): String = "Fish File"
}
