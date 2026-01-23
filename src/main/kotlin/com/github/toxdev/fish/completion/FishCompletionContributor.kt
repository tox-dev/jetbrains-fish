package com.github.toxdev.fish.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns

class FishCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(),
            FishKeywordCompletionProvider(),
        )

        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(),
            FishBuiltinCompletionProvider(),
        )

        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(),
            FishFunctionCompletionProvider(),
        )
    }
}
