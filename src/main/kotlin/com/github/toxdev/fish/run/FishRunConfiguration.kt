package com.github.toxdev.fish.run

import com.intellij.execution.Executor
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.configurations.RuntimeConfigurationException
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import org.jdom.Element
import java.io.File

class FishRunConfiguration(
    project: Project,
    factory: ConfigurationFactory,
    name: String,
) : RunConfigurationBase<FishRunConfigurationOptions>(project, factory, name) {
    var scriptPath: String
        get() = options.scriptPath
        set(value) {
            options.scriptPath = value
        }

    var scriptArguments: String
        get() = options.scriptArguments
        set(value) {
            options.scriptArguments = value
        }

    var workingDirectory: String
        get() = options.workingDirectory
        set(value) {
            options.workingDirectory = value
        }

    var fishPath: String
        get() = options.fishPath
        set(value) {
            options.fishPath = value
        }

    override fun getOptions(): FishRunConfigurationOptions = super.getOptions() as FishRunConfigurationOptions

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> = FishRunConfigurationEditor(project)

    override fun checkConfiguration() {
        if (scriptPath.isBlank()) {
            throw RuntimeConfigurationException("Script path is not specified")
        }
        if (!File(scriptPath).exists()) {
            throw RuntimeConfigurationException("Script file does not exist: $scriptPath")
        }
        val fish = fishPath.ifBlank { findFishExecutable() }
        if (fish == null || !File(fish).exists()) {
            throw RuntimeConfigurationException("Fish shell executable not found")
        }
    }

    override fun getState(
        executor: Executor,
        environment: ExecutionEnvironment,
    ): RunProfileState = FishRunProfileState(environment, this)

    override fun readExternal(element: Element) {
        super.readExternal(element)
    }

    override fun writeExternal(element: Element) {
        super.writeExternal(element)
    }

    companion object {
        fun findFishExecutable(): String? {
            val pathEnv = System.getenv("PATH") ?: return null
            for (path in pathEnv.split(File.pathSeparator)) {
                val executable = File(path, "fish")
                if (executable.exists() && executable.canExecute()) {
                    return executable.absolutePath
                }
            }
            val commonPaths = listOf("/usr/local/bin/fish", "/opt/homebrew/bin/fish", "/usr/bin/fish")
            for (path in commonPaths) {
                val executable = File(path)
                if (executable.exists() && executable.canExecute()) {
                    return executable.absolutePath
                }
            }
            return null
        }
    }
}
