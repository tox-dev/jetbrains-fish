package com.github.toxdev.fish.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext

class FishKeywordCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet,
    ) {
        KEYWORDS.forEach { keyword ->
            result.addElement(
                LookupElementBuilder
                    .create(keyword)
                    .bold(),
            )
        }
    }

    companion object {
        private val KEYWORDS =
            setOf(
                "and",
                "begin",
                "break",
                "builtin",
                "case",
                "command",
                "continue",
                "else",
                "end",
                "exec",
                "for",
                "function",
                "if",
                "in",
                "not",
                "or",
                "return",
                "set",
                "switch",
                "time",
                "while",
            )
    }
}
