package com.github.toxdev.fish

import com.github.toxdev.fish.pages.IdeaFrame
import com.github.toxdev.fish.pages.idea
import com.intellij.remoterobot.RemoteRobot
import com.intellij.remoterobot.stepsProcessing.StepLogger
import com.intellij.remoterobot.stepsProcessing.StepWorker
import com.intellij.remoterobot.utils.waitFor
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.TestWatcher
import java.io.File
import java.time.Duration.ofMinutes
import java.time.Duration.ofSeconds
import java.util.concurrent.TimeUnit
import javax.imageio.ImageIO

@ExtendWith(UITest.IdeTestWatcher::class)
@Timeout(value = 15, unit = TimeUnit.MINUTES)
class UITest {
    class IdeTestWatcher : TestWatcher {
        override fun testFailed(
            context: ExtensionContext,
            cause: Throwable?,
        ) {
            File("build/reports").mkdirs()
            ImageIO.write(
                remoteRobot.getScreenshot(),
                "png",
                File("build/reports", "${context.displayName}.png"),
            )
        }
    }

    companion object {
        private lateinit var remoteRobot: RemoteRobot

        @BeforeAll
        @JvmStatic
        @Timeout(value = 5, unit = TimeUnit.MINUTES)
        fun startIdea() {
            // The demo project is prepared and opened by the runIdeForUiTests task, so the IDE comes
            // up with it already loaded. Gate on the project tree showing the file (a UI signal that
            // works during indexing) rather than a callJs dumb-mode probe, which is slower to settle
            // on a cold headless run.
            StepWorker.registerProcessor(StepLogger())
            remoteRobot = RemoteRobot("http://127.0.0.1:8082")
            remoteRobot.find<IdeaFrame>(timeout = ofMinutes(2)).apply {
                waitFor(ofMinutes(3)) { projectViewTree.hasText("test.fish") }
            }
        }

        @AfterAll
        @JvmStatic
        fun cleanUp() {
        }
    }

    @Test
    fun testFishFileRecognized() {
        remoteRobot.idea {
            with(projectViewTree) {
                waitFor(ofSeconds(30)) { hasText("test.fish") }
            }
        }
    }

    @Test
    fun testOpenFishFile() {
        remoteRobot.idea {
            waitFor(ofSeconds(30)) { projectViewTree.hasText("test.fish") }
            waitFor(ofSeconds(60)) {
                openProjectFile("test.fish")
                openFileNames().contains("test.fish")
            }
        }
    }

    @Test
    fun testFishFileHasSyntaxHighlighting() {
        remoteRobot.idea {
            waitFor(ofSeconds(30)) { projectViewTree.hasText("test.fish") }
            waitFor(ofSeconds(60)) {
                openProjectFile("test.fish")
                openFileType("test.fish") == "Fish"
            }
        }
    }
}
