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

    val editorTabs
        get() =
            find<ContainerFixture>(
                byXpath("EditorTabs", "//div[@class='EditorTabs']"),
                Duration.ofSeconds(10),
            )

    fun isDumbMode(): Boolean =
        callJs(
            """
            const frameHelper = com.intellij.openapi.wm.impl.ProjectFrameHelper.getFrameHelper(component)
            if (frameHelper) {
                const project = frameHelper.getProject()
                project ? com.intellij.openapi.project.DumbService.isDumb(project) : true
            } else {
                true
            }
        """,
            true,
        )
}
