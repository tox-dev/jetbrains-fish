package com.github.toxdev.fish.run

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.ColoredProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import java.io.File

class FishRunProfileState(
    environment: ExecutionEnvironment,
    private val configuration: FishRunConfiguration,
) : CommandLineState(environment) {
    override fun startProcess(): ProcessHandler {
        val fishPath =
            configuration.fishPath.ifBlank { FishRunConfiguration.findFishExecutable() }
                ?: throw ExecutionException("Fish shell executable not found")
        val commandLine = GeneralCommandLine(fishPath)
        commandLine.addParameter(configuration.scriptPath)
        if (configuration.scriptArguments.isNotBlank()) {
            for (arg in configuration.scriptArguments.split("\\s+".toRegex())) {
                if (arg.isNotBlank()) {
                    commandLine.addParameter(arg)
                }
            }
        }
        val workingDir = configuration.workingDirectory.ifBlank { File(configuration.scriptPath).parent }
        if (!workingDir.isNullOrBlank()) {
            commandLine.workDirectory = File(workingDir)
        }
        commandLine.charset = Charsets.UTF_8
        val handler = ColoredProcessHandler(commandLine)
        ProcessTerminatedListener.attach(handler)
        return handler
    }
}
