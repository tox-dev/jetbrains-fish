package com.github.toxdev.fish.pages

import com.intellij.remoterobot.RemoteRobot
import com.intellij.remoterobot.data.RemoteComponent
import com.intellij.remoterobot.fixtures.CommonContainerFixture
import com.intellij.remoterobot.fixtures.ContainerFixture
import com.intellij.remoterobot.fixtures.DefaultXpath
import com.intellij.remoterobot.fixtures.FixtureName
import com.intellij.remoterobot.search.locators.byXpath
import java.time.Duration

fun RemoteRobot.idea(function: IdeaFrame.() -> Unit) {
    find<IdeaFrame>(timeout = Duration.ofSeconds(10)).apply(function)
}

@FixtureName("Idea frame")
@DefaultXpath("IdeFrameImpl type", "//div[@class='IdeFrameImpl']")
class IdeaFrame(
    remoteRobot: RemoteRobot,
    remoteComponent: RemoteComponent,
) : CommonContainerFixture(remoteRobot, remoteComponent) {
    val projectViewTree
        get() =
            find<ContainerFixture>(
                byXpath("ProjectViewTree", "//div[contains(@class, 'ProjectViewTree')]"),
                Duration.ofSeconds(30),
            )

    // Drive the editor through the platform rather than the mouse: robot clicks depend on the IDE
    // window being frontmost, which is not guaranteed, and the tab widgets are unstable across
    // releases. Opening and inspecting via the API is what the tests actually care about.
    fun openProjectFile(name: String) =
        runJs(
            """
            const project = com.intellij.openapi.wm.impl.ProjectFrameHelper.getFrameHelper(component).getProject()
            const dir = com.intellij.openapi.project.ProjectUtil.guessProjectDir(project)
            const file = dir.findFileByRelativePath('$name')
            if (file != null) {
                com.intellij.openapi.fileEditor.FileEditorManager.getInstance(project).openFile(file, true)
            }
            """,
            true,
        )

    fun openFileNames(): List<String> =
        callJs<String>(OPEN_FILES_SCRIPT, true)
            .split("\n")
            .filter { it.isNotEmpty() }

    fun openFileType(name: String): String =
        callJs(
            """
            const project = com.intellij.openapi.wm.impl.ProjectFrameHelper.getFrameHelper(component).getProject()
            const files = com.intellij.openapi.fileEditor.FileEditorManager.getInstance(project).getOpenFiles()
            let type = ""
            for (let i = 0; i < files.length; i++) { if (files[i].getName() == '$name') { type = files[i].getFileType().getName() } }
            type
            """,
            true,
        )

    private companion object {
        const val OPEN_FILES_SCRIPT =
            """
            const project = com.intellij.openapi.wm.impl.ProjectFrameHelper.getFrameHelper(component).getProject()
            const files = com.intellij.openapi.fileEditor.FileEditorManager.getInstance(project).getOpenFiles()
            const names = []
            for (let i = 0; i < files.length; i++) { names.push(files[i].getName()) }
            names.join('\n')
            """
    }
}
