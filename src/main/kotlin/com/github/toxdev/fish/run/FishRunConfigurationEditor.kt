package com.github.toxdev.fish.run

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class FishRunConfigurationEditor(
    private val project: Project,
) : SettingsEditor<FishRunConfiguration>() {
    private val scriptPathField = TextFieldWithBrowseButton()
    private val scriptArgumentsField = JBTextField()
    private val workingDirectoryField = TextFieldWithBrowseButton()
    private val fishPathField = TextFieldWithBrowseButton()

    init {
        scriptPathField.addBrowseFolderListener(
            project,
            FileChooserDescriptorFactory
                .createSingleFileDescriptor("fish")
                .withTitle("Select Fish Script")
                .withDescription("Select the Fish script to run"),
        )
        workingDirectoryField.addBrowseFolderListener(
            project,
            FileChooserDescriptorFactory
                .createSingleFolderDescriptor()
                .withTitle("Select Working Directory")
                .withDescription("Select the working directory for the script"),
        )
        fishPathField.addBrowseFolderListener(
            project,
            FileChooserDescriptorFactory
                .createSingleFileDescriptor()
                .withTitle("Select Fish Executable")
                .withDescription("Select the Fish shell executable"),
        )
    }

    override fun resetEditorFrom(configuration: FishRunConfiguration) {
        scriptPathField.text = configuration.scriptPath
        scriptArgumentsField.text = configuration.scriptArguments
        workingDirectoryField.text = configuration.workingDirectory
        fishPathField.text = configuration.fishPath
    }

    override fun applyEditorTo(configuration: FishRunConfiguration) {
        configuration.scriptPath = scriptPathField.text
        configuration.scriptArguments = scriptArgumentsField.text
        configuration.workingDirectory = workingDirectoryField.text
        configuration.fishPath = fishPathField.text
    }

    override fun createEditor(): JComponent =
        panel {
            row("Script path:") {
                cell(scriptPathField).align(AlignX.FILL)
            }
            row("Script arguments:") {
                cell(scriptArgumentsField).align(AlignX.FILL)
            }
            row("Working directory:") {
                cell(workingDirectoryField).align(AlignX.FILL)
            }
            row("Fish executable:") {
                cell(fishPathField).align(AlignX.FILL).comment("Leave empty to use fish from PATH")
            }
        }
}
