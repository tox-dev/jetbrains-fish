package com.github.toxdev.fish.documentation

import com.github.toxdev.fish.completion.FishBuiltinCompletionProvider
import com.github.toxdev.fish.psi.FishTokenTypes
import com.github.toxdev.fish.psi.FishTypes
import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.elementType

class FishDocumentationProvider : AbstractDocumentationProvider() {
    override fun generateDoc(
        element: PsiElement?,
        originalElement: PsiElement?,
    ): String? {
        val target = element ?: originalElement ?: return null
        return when (target.elementType) {
            FishTypes.VARIABLE -> FishDocumentation.getVariableDoc(target.text)
            FishTypes.WORD -> getWordDocumentation(target)
            FishTypes.ARGUMENT -> getArgumentDocumentation(target)
            FishTypes.PIPE -> FishDocumentation.getOperatorDoc("|")
            FishTypes.AND_AND -> FishDocumentation.getOperatorDoc("&&")
            FishTypes.OR_OR -> FishDocumentation.getOperatorDoc("||")
            FishTypes.BACKGROUND -> FishDocumentation.getOperatorDoc("&")
            FishTypes.REDIRECT -> getRedirectDocumentation(target.text)
            FishTokenTypes.KEYWORD -> FishDocumentation.getCommandDoc(target.text)
            else -> null
        }
    }

    override fun getCustomDocumentationElement(
        editor: Editor,
        file: PsiFile,
        contextElement: PsiElement?,
        targetOffset: Int,
    ): PsiElement? = contextElement

    private fun getArgumentDocumentation(element: PsiElement): String? {
        val word = element.children.firstOrNull { it.elementType == FishTypes.WORD } ?: return null
        return getWordDocumentation(word)
    }

    private fun getWordDocumentation(element: PsiElement): String? {
        val text = element.text
        if (text in FishTokenTypes.KEYWORD_TEXTS) {
            return FishDocumentation.getCommandDoc(text)
        }
        if (isCommandPosition(element)) {
            return FishDocumentation.getCommandDoc(text)
        }
        getSubcommandDoc(element, text)?.let { return it }
        return null
    }

    private fun getSubcommandDoc(
        element: PsiElement,
        subcommand: String,
    ): String? {
        val parentCommand = findPrecedingCommand(element) ?: return null
        if (parentCommand !in COMMANDS_WITH_SUBCOMMANDS) return null
        return FishDocumentation.getCommandDoc("$parentCommand $subcommand")
    }

    private fun findPrecedingCommand(element: PsiElement) = findPrecedingWord(element) ?: findPrecedingWordViaParent(element)

    private fun findPrecedingWord(element: PsiElement): String? {
        var prev = element.prevSibling
        while (prev != null) {
            when (prev.elementType) {
                FishTokenTypes.WHITE_SPACE -> prev = prev.prevSibling
                FishTypes.WORD -> return prev.text
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
                when (prev.elementType) {
                    FishTokenTypes.WHITE_SPACE -> prev = prev.prevSibling
                    FishTypes.WORD -> return prev.text
                    else -> {
                        val word = prev.children.firstOrNull { it.elementType == FishTypes.WORD }
                        if (word != null) return word.text
                        prev = prev.prevSibling
                    }
                }
            }
            current = current.parent
        }
        return null
    }

    private fun isCommandPosition(element: PsiElement): Boolean {
        if (!isFirstInParent(element)) return false
        var current = element.parent
        while (current != null) {
            if (!isFirstInParent(current)) return false
            if (current.elementType == FishTypes.ARGUMENT_LIST) return false
            current = current.parent
        }
        return true
    }

    private fun isFirstInParent(element: PsiElement): Boolean {
        var prev = element.prevSibling
        while (prev != null) {
            when (prev.elementType) {
                FishTokenTypes.WHITE_SPACE -> prev = prev.prevSibling
                FishTypes.NEWLINE,
                FishTypes.SEMICOLON,
                FishTypes.PIPE,
                FishTypes.AND_AND,
                FishTypes.OR_OR,
                -> return true
                else -> return false
            }
        }
        return true
    }

    private fun getRedirectDocumentation(text: String): String? =
        when {
            text.startsWith("&>") || text.startsWith(">&") -> FishDocumentation.getOperatorDoc("&>")
            text.startsWith(">>") -> FishDocumentation.getOperatorDoc(">>")
            text.startsWith("2>") -> FishDocumentation.getOperatorDoc("2>")
            text.startsWith(">") -> FishDocumentation.getOperatorDoc(">")
            text.startsWith("<") -> FishDocumentation.getOperatorDoc("<")
            else -> null
        }

    companion object {
        private val COMMANDS_WITH_SUBCOMMANDS = setOf("string", "status", "path", "history")

        private val BUILTINS: Set<String> by lazy {
            try {
                val field = FishBuiltinCompletionProvider::class.java.getDeclaredField("BUILTINS")
                field.isAccessible = true
                @Suppress("UNCHECKED_CAST")
                field.get(FishBuiltinCompletionProvider.Companion) as Set<String>
            } catch (_: Exception) {
                emptySet()
            }
        }
    }
}
