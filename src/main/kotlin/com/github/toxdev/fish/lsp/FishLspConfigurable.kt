package com.github.toxdev.fish.lsp

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.BoundConfigurable
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel

class FishLspConfigurable : BoundConfigurable("Fish Shell") {
    private val settings = FishLspSettings.getInstance()

    override fun createPanel(): DialogPanel =
        panel {
            group("Language Server") {
                row("fish-lsp path:") {
                    textFieldWithBrowseButton(
                        fileChooserDescriptor =
                            FileChooserDescriptorFactory
                                .createSingleFileDescriptor()
                                .withTitle("Select fish-lsp Executable"),
                    ).align(AlignX.FILL)
                        .bindText(settings::fishLspPath)
                        .comment("Leave blank to auto-detect from PATH")
                }
                row {
                    val detected = FishLspSettings.findFishLspInPath()
                    if (detected != null) {
                        text("Detected: $detected")
                    } else {
                        text("fish-lsp not found in PATH")
                    }
                }
                row {
                    browserLink(
                        "Installation instructions",
                        "https://github.com/ndonfris/fish-lsp#installation",
                    )
                }
            }
        }
}
