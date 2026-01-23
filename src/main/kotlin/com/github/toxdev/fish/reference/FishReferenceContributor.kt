package com.github.toxdev.fish.reference

import com.github.toxdev.fish.psi.FishTypes
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceRegistrar

class FishReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(FishTypes.WORD),
            FishFunctionReferenceProvider(),
        )
    }
}
