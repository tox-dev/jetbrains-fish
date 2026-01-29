package com.github.toxdev.fish.run

import com.github.toxdev.fish.psi.FishFile
import com.intellij.execution.ExecutionManager
import com.intellij.execution.RunManager
import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.RunConfigurationProducer
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.runners.ExecutionEnvironmentBuilder
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.DumbAwareAction

class FishRunFileAction : DumbAwareAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val file = e.getData(CommonDataKeys.PSI_FILE) ?: return
        val virtualFile = file.virtualFile ?: return
        val project = file.project

        val context = ConfigurationContext.getFromContext(e.dataContext, e.place)
        val configProducer = RunConfigurationProducer.getInstance(FishRunConfigurationProducer::class.java)
        var configurationSettings = configProducer.findExistingConfiguration(context)

        if (configurationSettings == null) {
            configurationSettings =
                RunManager
                    .getInstance(project)
                    .createConfiguration(file.name, FishRunConfigurationType::class.java)
            val runConfiguration = configurationSettings.configuration as FishRunConfiguration
            runConfiguration.scriptPath = virtualFile.path
            runConfiguration.workingDirectory = virtualFile.parent?.path ?: ""
        }

        val builder =
            ExecutionEnvironmentBuilder.createOrNull(
                DefaultRunExecutor.getRunExecutorInstance(),
                configurationSettings.configuration,
            )
        if (builder != null) {
            ExecutionManager.getInstance(project).restartRunProfile(builder.build())
        }
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        val file = e.getData(CommonDataKeys.PSI_FILE)
        e.presentation.isEnabledAndVisible = project != null && file is FishFile
    }

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    companion object {
        const val ID = "runFishFileAction"
    }
}
