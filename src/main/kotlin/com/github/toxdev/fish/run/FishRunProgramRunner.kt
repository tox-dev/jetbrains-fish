package com.github.toxdev.fish.run

import com.intellij.execution.ExecutionException
import com.intellij.execution.ExecutionManager
import com.intellij.execution.configurations.RunProfile
import com.intellij.execution.configurations.RunnerSettings
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.runners.ProgramRunner
import com.intellij.execution.runners.showRunContent
import com.intellij.openapi.fileEditor.FileDocumentManager

class FishRunProgramRunner : ProgramRunner<RunnerSettings> {
    override fun getRunnerId(): String = "fishRunRunner"

    override fun canRun(
        executorId: String,
        profile: RunProfile,
    ): Boolean = DefaultRunExecutor.EXECUTOR_ID == executorId && profile is FishRunConfiguration

    @Throws(ExecutionException::class)
    override fun execute(environment: ExecutionEnvironment) {
        ExecutionManager.getInstance(environment.getProject()).startRunProfile(environment) { state ->
            FileDocumentManager.getInstance().saveAllDocuments()
            showRunContent(state.execute(environment.executor, this), environment)
        }
    }
}
