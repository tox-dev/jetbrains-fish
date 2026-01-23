package com.github.toxdev.fish.formatting

import com.github.toxdev.fish.FishFileType
import com.intellij.formatting.service.AsyncDocumentFormattingService
import com.intellij.formatting.service.AsyncFormattingRequest
import com.intellij.formatting.service.FormattingService
import com.intellij.psi.PsiFile
import java.io.File
import java.nio.charset.StandardCharsets
import java.util.EnumSet
import java.util.concurrent.TimeUnit

class FishExternalFormatter : AsyncDocumentFormattingService() {
    override fun getFeatures(): MutableSet<FormattingService.Feature> = EnumSet.noneOf(FormattingService.Feature::class.java)

    override fun canFormat(file: PsiFile): Boolean = file.fileType == FishFileType.INSTANCE && findFishIndent() != null

    override fun getNotificationGroupId(): String = "Fish Shell"

    override fun getName(): String = "fish_indent"

    override fun createFormattingTask(request: AsyncFormattingRequest): FormattingTask {
        val ioFile = request.ioFile
        return object : FormattingTask {
            private var cancelled = false

            override fun run() {
                if (cancelled) {
                    request.onTextReady(null)
                    return
                }
                val fishIndentPath = findFishIndent()
                if (fishIndentPath == null) {
                    request.onTextReady(null)
                    return
                }
                val formatted = runFishIndent(fishIndentPath, request.documentText, ioFile)
                if (cancelled) {
                    request.onTextReady(null)
                    return
                }
                request.onTextReady(formatted)
            }

            override fun cancel(): Boolean {
                cancelled = true
                return true
            }

            override fun isRunUnderProgress(): Boolean = true
        }
    }

    companion object {
        private var cachedFishIndentPath: String? = null
        private var cacheChecked = false

        fun findFishIndent(): String? {
            if (cacheChecked) return cachedFishIndentPath
            cachedFishIndentPath = findFishIndentExecutable()
            cacheChecked = true
            return cachedFishIndentPath
        }

        internal fun resetCache() {
            cachedFishIndentPath = null
            cacheChecked = false
        }

        private fun findFishIndentExecutable(): String? {
            val pathEnv = System.getenv("PATH") ?: return null
            val pathSeparator = File.pathSeparator
            val paths = pathEnv.split(pathSeparator)
            for (path in paths) {
                val executable = File(path, "fish_indent")
                if (executable.exists() && executable.canExecute()) {
                    return executable.absolutePath
                }
            }
            val commonPaths = listOf("/usr/local/bin/fish_indent", "/opt/homebrew/bin/fish_indent", "/usr/bin/fish_indent")
            for (path in commonPaths) {
                val executable = File(path)
                if (executable.exists() && executable.canExecute()) {
                    return executable.absolutePath
                }
            }
            return null
        }

        internal fun runFishIndent(
            fishIndentPath: String,
            content: String,
            workingDir: File?,
        ): String? {
            val processBuilder = ProcessBuilder(fishIndentPath)
            if (workingDir?.parentFile?.exists() == true) {
                processBuilder.directory(workingDir.parentFile)
            }
            processBuilder.redirectErrorStream(true)
            val process =
                try {
                    processBuilder.start()
                } catch (_: java.io.IOException) {
                    return null
                }
            try {
                process.outputStream.use { outputStream ->
                    outputStream.write(content.toByteArray(StandardCharsets.UTF_8))
                }
            } catch (_: java.io.IOException) {
                process.destroyForcibly()
                return null
            }
            val completed = process.waitFor(10, TimeUnit.SECONDS)
            if (!completed) {
                process.destroyForcibly()
                return null
            }
            if (process.exitValue() != 0) return null
            return process.inputStream.bufferedReader(StandardCharsets.UTF_8).readText()
        }
    }
}
