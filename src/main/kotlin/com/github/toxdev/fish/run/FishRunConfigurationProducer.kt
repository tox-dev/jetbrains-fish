package com.github.toxdev.fish.run

import com.github.toxdev.fish.psi.FishFile
import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.LazyRunConfigurationProducer
import com.intellij.execution.configurations.ConfigurationTypeUtil
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement

class FishRunConfigurationProducer : LazyRunConfigurationProducer<FishRunConfiguration>() {
    override fun getConfigurationFactory() =
        ConfigurationTypeUtil.findConfigurationType(FishRunConfigurationType::class.java).configurationFactories[0]

    override fun setupConfigurationFromContext(
        configuration: FishRunConfiguration,
        context: ConfigurationContext,
        sourceElement: Ref<PsiElement>,
    ): Boolean {
        val psiFile = findFishFile(sourceElement.get()) ?: return false
        val virtualFile = psiFile.virtualFile ?: return false

        configuration.scriptPath = virtualFile.path
        configuration.workingDirectory = virtualFile.parent?.path ?: ""
        configuration.name = virtualFile.presentableName
        return true
    }

    override fun isConfigurationFromContext(
        configuration: FishRunConfiguration,
        context: ConfigurationContext,
    ): Boolean {
        val psiFile = findFishFile(context.psiLocation) ?: return false
        val virtualFile = psiFile.virtualFile ?: return false

        return configuration.scriptPath == virtualFile.path &&
            configuration.workingDirectory == (virtualFile.parent?.path ?: "")
    }

    private fun findFishFile(element: PsiElement?): FishFile? {
        if (element == null) return null
        if (element is FishFile) return element
        val containingFile = element.containingFile
        return containingFile as? FishFile
    }
}
