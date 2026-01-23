package com.github.toxdev.fish.completion

import com.github.toxdev.fish.psi.FishFile
import com.github.toxdev.fish.psi.FishFunctionBlock
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext

class FishFunctionCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet,
    ) {
        val file = parameters.originalFile as? FishFile ?: return

        val functions = PsiTreeUtil.findChildrenOfType(file, FishFunctionBlock::class.java)
        functions.forEach { function ->
            val name = function.functionName?.text ?: return@forEach
            result.addElement(
                LookupElementBuilder
                    .create(name)
                    .withTypeText("function"),
            )
        }
    }
}
