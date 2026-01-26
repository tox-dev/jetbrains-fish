package com.github.toxdev.fish.inspection

import com.github.toxdev.fish.psi.FishArgumentList
import com.github.toxdev.fish.psi.FishFunctionBlock
import com.github.toxdev.fish.psi.FishTypes
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil

class FishUnusedVariableInspection : LocalInspectionTool() {
    override fun getDisplayName(): String = "Unused local variable"

    override fun getShortName(): String = "FishUnusedVariable"

    override fun getGroupDisplayName(): String = "Fish"

    override fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean,
    ): PsiElementVisitor =
        object : PsiElementVisitor() {
            override fun visitElement(element: PsiElement) {
                if (element is FishFunctionBlock) {
                    checkUnusedVariablesInFunction(element, holder)
                }
            }
        }

    private fun checkUnusedVariablesInFunction(
        function: FishFunctionBlock,
        holder: ProblemsHolder,
    ) {
        val setCommands = findSetCommands(function)
        val localVariables = mutableMapOf<String, PsiElement>()
        for ((setElement, args) in setCommands) {
            if (!isLocalSet(args)) continue
            val varName = findVariableName(args) ?: continue
            if (!isBuiltinVariable(varName)) {
                localVariables[varName] = setElement
            }
        }
        val usedVariables = findUsedVariables(function)
        for ((varName, element) in localVariables) {
            if (varName !in usedVariables) {
                holder.registerProblem(element, "Local variable '$varName' is never used")
            }
        }
    }

    private fun findSetCommands(function: FishFunctionBlock): List<Pair<PsiElement, FishArgumentList>> {
        val result = mutableListOf<Pair<PsiElement, FishArgumentList>>()
        PsiTreeUtil.processElements(function) { element ->
            if (element.node.elementType == FishTypes.WORD && element.text == "set") {
                val nextSibling = PsiTreeUtil.skipWhitespacesForward(element)
                if (nextSibling is FishArgumentList) {
                    result.add(element to nextSibling)
                }
            }
            true
        }
        return result
    }

    private fun isLocalSet(args: FishArgumentList): Boolean = args.argumentList.any { it.text == "-l" || it.text == "--local" }

    private fun findVariableName(args: FishArgumentList): String? {
        val argList = args.argumentList
        for (arg in argList) {
            val text = arg.text
            if (!text.startsWith("-") && !text.startsWith("$")) {
                return text
            }
        }
        return null
    }

    private fun isBuiltinVariable(name: String): Boolean =
        name in
            setOf(
                "argv",
                "status",
                "pipestatus",
                "fish_pid",
                "last_pid",
                "CMD_DURATION",
                "fish_kill_signal",
                "_",
                "history",
                "HOME",
                "USER",
                "PWD",
                "SHLVL",
                "fish_greeting",
            )

    private fun findUsedVariables(function: FishFunctionBlock): Set<String> {
        val used = mutableSetOf<String>()
        PsiTreeUtil.processElements(function) { element ->
            if (element.node.elementType == FishTypes.VARIABLE) {
                val varText = element.text
                val varName =
                    when {
                        varText.startsWith("\$") -> varText.substring(1).takeWhile { it.isLetterOrDigit() || it == '_' }
                        else -> varText
                    }
                if (varName.isNotEmpty()) {
                    used.add(varName)
                }
            }
            true
        }
        return used
    }
}
