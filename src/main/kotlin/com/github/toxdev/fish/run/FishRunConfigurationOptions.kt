package com.github.toxdev.fish.run

import com.intellij.execution.configurations.RunConfigurationOptions
import com.intellij.openapi.components.StoredProperty

class FishRunConfigurationOptions : RunConfigurationOptions() {
    private val scriptPathProperty: StoredProperty<String?> = string("").provideDelegate(this, "scriptPath")
    private val scriptArgumentsProperty: StoredProperty<String?> = string("").provideDelegate(this, "scriptArguments")
    private val workingDirectoryProperty: StoredProperty<String?> = string("").provideDelegate(this, "workingDirectory")
    private val fishPathProperty: StoredProperty<String?> = string("").provideDelegate(this, "fishPath")

    var scriptPath: String
        get() = scriptPathProperty.getValue(this) ?: ""
        set(value) = scriptPathProperty.setValue(this, value)

    var scriptArguments: String
        get() = scriptArgumentsProperty.getValue(this) ?: ""
        set(value) = scriptArgumentsProperty.setValue(this, value)

    var workingDirectory: String
        get() = workingDirectoryProperty.getValue(this) ?: ""
        set(value) = workingDirectoryProperty.setValue(this, value)

    var fishPath: String
        get() = fishPathProperty.getValue(this) ?: ""
        set(value) = fishPathProperty.setValue(this, value)
}
