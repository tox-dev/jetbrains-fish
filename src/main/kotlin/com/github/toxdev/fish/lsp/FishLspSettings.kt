package com.github.toxdev.fish.lsp

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import java.io.File

@Service(Service.Level.APP)
@State(name = "FishLspSettings", storages = [Storage("fish-lsp.xml")])
class FishLspSettings : PersistentStateComponent<FishLspSettings.State> {
    data class State(
        var fishLspPath: String = "",
        var notificationDismissed: Boolean = false,
    )

    private var state = State()

    override fun getState(): State = state

    override fun loadState(state: State) {
        this.state = state
    }

    var fishLspPath: String
        get() = state.fishLspPath
        set(value) {
            state.fishLspPath = value
        }

    var notificationDismissed: Boolean
        get() = state.notificationDismissed
        set(value) {
            state.notificationDismissed = value
        }

    fun getEffectivePath(): String = fishLspPath.ifBlank { findFishLspInPath() ?: "" }

    companion object {
        fun getInstance(): FishLspSettings = service()

        fun findFishLspInPath(): String? {
            val pathEnv = System.getenv("PATH") ?: return null
            val pathSeparator = File.pathSeparator
            val executableName = "fish-lsp"

            return pathEnv
                .split(pathSeparator)
                .map { File(it, executableName) }
                .firstOrNull { it.exists() && it.canExecute() }
                ?.absolutePath
        }

        fun isFishLspAvailable(): Boolean = getInstance().getEffectivePath().isNotBlank()
    }
}
