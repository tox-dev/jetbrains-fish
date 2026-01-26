package com.github.toxdev.fish.inspection

import com.github.toxdev.fish.psi.FishArgumentList
import com.github.toxdev.fish.psi.FishCommandStatement
import com.github.toxdev.fish.psi.FishTypes
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil

class FishDeprecatedSyntaxInspection : LocalInspectionTool() {
    override fun getDisplayName(): String = "Deprecated Fish syntax"

    override fun getShortName(): String = "FishDeprecatedSyntax"

    override fun getGroupDisplayName(): String = "Fish"

    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean,
    ): PsiElementVisitor =
        object : PsiElementVisitor() {
            override fun visitElement(element: PsiElement) {
                when {
                    element.node.elementType == FishTypes.VARIABLE -> checkDeprecatedVariable(element, holder)
                    element.node.elementType == FishTypes.WORD -> checkDeprecatedFunction(element, holder)
                    element is FishCommandStatement -> checkDeprecatedCommand(element, holder)
                }
            }
        }

    private fun checkDeprecatedVariable(
        element: PsiElement,
        holder: ProblemsHolder,
    ) {
        if (element.text == "\$_") {
            holder.registerProblem(
                element,
                "Variable '\$_' is deprecated since Fish 2.0, use 'status current-command' instead",
            )
        }
    }

    private fun checkDeprecatedFunction(
        element: PsiElement,
        holder: ProblemsHolder,
    ) {
        val text = element.text
        val replacement = DEPRECATED_FUNCTIONS[text] ?: return
        if (!isCommandPosition(element)) return
        holder.registerProblem(element, "Function '$text' is deprecated, use '$replacement' instead")
    }

    private fun isCommandPosition(element: PsiElement): Boolean {
        val parent = element.parent ?: return false
        if (parent is FishCommandStatement) {
            val firstWord = findFirstWord(parent)
            return firstWord == element
        }
        return false
    }

    private fun findFirstWord(statement: FishCommandStatement): PsiElement? {
        var child = statement.firstChild
        while (child != null) {
            if (child.node.elementType == FishTypes.WORD) {
                return child
            }
            child = child.nextSibling
        }
        return null
    }

    private fun checkDeprecatedCommand(
        statement: FishCommandStatement,
        holder: ProblemsHolder,
    ) {
        val commandWord = findFirstWord(statement) ?: return
        val commandText = commandWord.text
        val args = PsiTreeUtil.findChildOfType(statement, FishArgumentList::class.java)
        when (commandText) {
            "read" -> checkReadDeprecation(args, holder)
            "history" -> checkHistoryDeprecation(args, holder)
        }
    }

    private fun checkReadDeprecation(
        args: FishArgumentList?,
        holder: ProblemsHolder,
    ) {
        val argList = args?.argumentList ?: return
        for (arg in argList) {
            if (arg.text == "-i") {
                holder.registerProblem(
                    arg,
                    "Usage of '-i' for silent mode is deprecated, use '-s' or '--silent' instead",
                )
            }
        }
    }

    private fun checkHistoryDeprecation(
        args: FishArgumentList?,
        holder: ProblemsHolder,
    ) {
        val argList = args?.argumentList ?: return
        for (arg in argList) {
            val replacement = DEPRECATED_HISTORY_FLAGS[arg.text]
            if (replacement != null) {
                holder.registerProblem(
                    arg,
                    "Flag '${arg.text}' is deprecated, use '$replacement' subcommand instead",
                )
            }
        }
    }

    companion object {
        val DEPRECATED_FUNCTIONS =
            mapOf(
                "__fish_git_prompt" to "fish_git_prompt",
                "__fish_vcs_prompt" to "fish_vcs_prompt",
                "__fish_hg_prompt" to "fish_hg_prompt",
                "__fish_svn_prompt" to "fish_svn_prompt",
                "__fish_pwd" to "prompt_pwd",
                "__fish_prepend_sudo" to "fish_commandline_prepend sudo",
                "N_" to "string (no localization needed)",
            )

        val DEPRECATED_HISTORY_FLAGS =
            mapOf(
                "--search" to "history search",
                "-S" to "history search",
                "--delete" to "history delete",
                "-D" to "history delete",
                "--save" to "history save",
                "-V" to "history save",
                "--clear" to "history clear",
                "-X" to "history clear",
                "--merge" to "history merge",
                "-M" to "history merge",
            )
    }
}
