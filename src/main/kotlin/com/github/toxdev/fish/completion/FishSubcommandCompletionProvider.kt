package com.github.toxdev.fish.completion

import com.github.toxdev.fish.psi.FishTokenTypes
import com.github.toxdev.fish.psi.FishTypes
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext

class FishSubcommandCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet,
    ) {
        val position = parameters.position
        val parentCommand = findPrecedingCommand(position) ?: return
        val subcommands = SUBCOMMANDS[parentCommand] ?: return

        subcommands.forEach { (name, description) ->
            result.addElement(
                LookupElementBuilder
                    .create(name)
                    .withTypeText(parentCommand)
                    .withTailText(" $description", true),
            )
        }
    }

    private fun findPrecedingCommand(element: PsiElement) = findPrecedingWord(element) ?: findPrecedingWordViaParent(element)

    private fun findPrecedingWord(element: PsiElement): String? {
        var prev = element.prevSibling
        while (prev != null) {
            when (prev.node?.elementType) {
                FishTokenTypes.WHITE_SPACE -> prev = prev.prevSibling
                FishTypes.WORD -> return prev.text.takeIf { it in SUBCOMMANDS }
                else -> return null
            }
        }
        return null
    }

    private fun findPrecedingWordViaParent(element: PsiElement): String? {
        var current = element.parent
        while (current != null) {
            var prev = current.prevSibling
            while (prev != null) {
                when (prev.node?.elementType) {
                    FishTokenTypes.WHITE_SPACE -> prev = prev.prevSibling
                    FishTypes.WORD -> return prev.text.takeIf { it in SUBCOMMANDS }
                    else -> {
                        val word = prev.children.firstOrNull { it.node?.elementType == FishTypes.WORD }
                        if (word != null) return word.text.takeIf { it in SUBCOMMANDS }
                        prev = prev.prevSibling
                    }
                }
            }
            current = current.parent
        }
        return null
    }

    companion object {
        private val SUBCOMMANDS =
            mapOf(
                "string" to
                    listOf(
                        "collect" to "collect slices into a string",
                        "escape" to "escape special characters",
                        "join" to "join strings with delimiter",
                        "join0" to "join strings with NUL",
                        "length" to "print string lengths",
                        "lower" to "convert to lowercase",
                        "match" to "match with regex",
                        "pad" to "pad strings to width",
                        "repeat" to "repeat strings",
                        "replace" to "replace substrings",
                        "shorten" to "shorten to width",
                        "split" to "split on delimiter",
                        "split0" to "split on NUL",
                        "sub" to "extract substring",
                        "trim" to "trim whitespace",
                        "unescape" to "unescape special characters",
                        "upper" to "convert to uppercase",
                    ),
                "status" to
                    listOf(
                        "basename" to "print script basename",
                        "current-command" to "print current command",
                        "dirname" to "print script directory",
                        "features" to "list feature flags",
                        "filename" to "print script filename",
                        "fish-path" to "print fish executable path",
                        "function" to "print current function",
                        "is-block" to "test if in block",
                        "is-command-substitution" to "test if in command substitution",
                        "is-full-job-control" to "test job control mode",
                        "is-interactive" to "test if interactive",
                        "is-interactive-job-control" to "test job control mode",
                        "is-login" to "test if login shell",
                        "is-no-job-control" to "test job control mode",
                        "job-control" to "get/set job control",
                        "line-number" to "print line number",
                        "stack-trace" to "print stack trace",
                        "test-feature" to "test feature flag",
                    ),
                "path" to
                    listOf(
                        "basename" to "get filename",
                        "change-extension" to "change extension",
                        "dirname" to "get directory",
                        "extension" to "get extension",
                        "filter" to "filter by type/permission",
                        "is" to "test path properties",
                        "mtime" to "get modification time",
                        "normalize" to "normalize path",
                        "resolve" to "resolve to absolute path",
                        "sort" to "sort paths",
                    ),
                "history" to
                    listOf(
                        "append" to "add to history",
                        "clear" to "clear all history",
                        "clear-session" to "clear session history",
                        "delete" to "delete entries",
                        "merge" to "merge from other sessions",
                        "save" to "save to file",
                        "search" to "search history",
                    ),
            )
    }
}
