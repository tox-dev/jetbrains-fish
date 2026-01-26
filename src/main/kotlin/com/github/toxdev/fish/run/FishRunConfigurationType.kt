package com.github.toxdev.fish.run

import com.github.toxdev.fish.FishIcons
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.openapi.project.Project
import javax.swing.Icon

class FishRunConfigurationType : ConfigurationType {
    override fun getDisplayName(): String = "Fish Script"

    override fun getConfigurationTypeDescription(): String = "Run a Fish shell script"

    override fun getIcon(): Icon = FishIcons.FILE

    override fun getId(): String = "FishRunConfiguration"

    override fun getConfigurationFactories(): Array<ConfigurationFactory> = arrayOf(FishConfigurationFactory(this))
}

class FishConfigurationFactory(
    type: ConfigurationType,
) : ConfigurationFactory(type) {
    override fun getId(): String = "FishConfigurationFactory"

    override fun createTemplateConfiguration(project: Project): RunConfiguration = FishRunConfiguration(project, this, "Fish Script")

    override fun getName(): String = "Fish Script"
}
