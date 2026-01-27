package com.github.toxdev.fish.pages

import com.intellij.remoterobot.RemoteRobot
import com.intellij.remoterobot.data.RemoteComponent
import com.intellij.remoterobot.fixtures.CommonContainerFixture
import com.intellij.remoterobot.fixtures.DefaultXpath
import com.intellij.remoterobot.fixtures.FixtureName
import com.intellij.remoterobot.search.locators.byXpath
import java.time.Duration

fun RemoteRobot.dialog(
    title: String,
    timeout: Duration = Duration.ofSeconds(20),
    function: DialogFixture.() -> Unit = {},
): DialogFixture = find<DialogFixture>(DialogFixture.byTitle(title), timeout).apply(function)

@FixtureName("Dialog")
@DefaultXpath("Dialog type", "//div[@class='MyDialog']")
class DialogFixture(
    remoteRobot: RemoteRobot,
    remoteComponent: RemoteComponent,
) : CommonContainerFixture(remoteRobot, remoteComponent) {
    companion object {
        @JvmStatic
        fun byTitle(title: String) = byXpath("title $title", "//div[@title='$title' and @class='MyDialog']")
    }

    val title: String
        get() = callJs("component.getTitle();")
}
