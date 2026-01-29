package com.github.toxdev.fish.run

import com.intellij.execution.configurations.RunProfile
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.configurations.RunnerSettings
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.runners.GenericProgramRunner
import com.intellij.execution.runners.RunContentBuilder
import com.intellij.execution.ui.RunContentDescriptor
import com.intellij.openapi.fileEditor.FileDocumentManager

class FishRunProgramRunner : GenericProgramRunner<RunnerSettings>() {
    override fun getRunnerId(): String = "fishRunRunner"

    override fun canRun(
        executorId: String,
        profile: RunProfile,
    ): Boolean = DefaultRunExecutor.EXECUTOR_ID == executorId && profile is FishRunConfiguration

    override fun doExecute(
        state: RunProfileState,
        environment: ExecutionEnvironment,
    ): RunContentDescriptor? {
        FileDocumentManager.getInstance().saveAllDocuments()
        val result = state.execute(environment.executor, this) ?: return null
        return RunContentBuilder(result, environment).showRunContent(environment.contentToReuse)
    }
}
