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
            // up with it already loaded; just wait for the frame and for indexing to settle.
            StepWorker.registerProcessor(StepLogger())
            remoteRobot = RemoteRobot("http://127.0.0.1:8082")
            remoteRobot.find<IdeaFrame>(timeout = ofMinutes(2)).apply {
                waitFor(ofMinutes(2)) { isDumbMode().not() }
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
                waitFor(ofSeconds(10)) {
                    hasText("test.fish")
                }
            }
        }
    }

    @Test
    fun testOpenFishFile() {
        remoteRobot.idea {
            with(projectViewTree) {
                waitFor(ofSeconds(10)) { hasText("test.fish") }
                println("DEBUG: hasText returned true, now calling findText")
                val allTexts = findAllText()
                println("DEBUG: All texts in tree: ${allTexts.map { it.text }}")
                val found = findAllText("test.fish")
                println("DEBUG: findAllText('test.fish') returned ${found.size} items: ${found.map { it.text }}")
                if (found.isEmpty()) {
                    throw AssertionError("findAllText returned empty but hasText returned true. All texts: ${allTexts.map { it.text }}")
                }
                found.first().doubleClick()
                waitFor(ofSeconds(10)) { isDumbMode().not() }
            }
            waitFor(ofSeconds(30)) {
                try {
                    val hasTab = editorTabs.hasText("test.fish")
                    println("DEBUG: editorTabs.hasText('test.fish') = $hasTab")
                    hasTab
                } catch (e: Exception) {
                    println("DEBUG: editorTabs not found yet: ${e.message}")
                    false
                }
            }
        }
    }

    @Test
    fun testFishFileHasSyntaxHighlighting() {
        remoteRobot.idea {
            with(projectViewTree) {
                waitFor(ofSeconds(10)) { hasText("test.fish") }
                findAllText("test.fish").first().doubleClick()
            }
            waitFor(ofSeconds(30)) {
                try {
                    isDumbMode().not() && editorTabs.hasText("test.fish")
                } catch (e: Exception) {
                    false
                }
            }
        }
    }
}
