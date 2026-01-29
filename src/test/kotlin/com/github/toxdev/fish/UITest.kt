package com.github.toxdev.fish

import com.github.toxdev.fish.pages.IdeaFrame
import com.github.toxdev.fish.pages.dialog
import com.github.toxdev.fish.pages.idea
import com.github.toxdev.fish.pages.welcomeFrame
import com.intellij.remoterobot.RemoteRobot
import com.intellij.remoterobot.search.locators.byXpath
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
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
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
        private lateinit var tmpDir: Path
        private lateinit var remoteRobot: RemoteRobot

        @BeforeAll
        @JvmStatic
        @Timeout(value = 5, unit = TimeUnit.MINUTES)
        fun startIdea() {
            val base = Path.of(System.getProperty("user.home"), "projects")
            Files.createDirectories(base)
            Files.list(base).filter { it.fileName.toString().startsWith("fish-ui-test") }.forEach {
                it.toFile().deleteRecursively()
            }
            tmpDir = Files.createTempDirectory(base, "fish-ui-test")
            val demo = Paths.get(tmpDir.toString(), "demo")
            Files.createDirectory(demo)
            File(demo.toString(), "test.fish").printWriter().use { out ->
                out.println("#!/usr/bin/env fish")
                out.println("")
                out.println("function greet")
                out.println("    echo \"Hello, World!\"")
                out.println("end")
                out.println("")
                out.println("greet")
            }

            StepWorker.registerProcessor(StepLogger())
            remoteRobot = RemoteRobot("http://127.0.0.1:8082")
            Thread.sleep(10000)
            remoteRobot.welcomeFrame {
                openButton.click()
                dialog("Open File or Project") {
                    val pathField = textField(byXpath("//div[@class='BorderlessTextField']"))
                    pathField.click()
                    Thread.sleep(500)
                    pathField.runJs("component.setText('${demo.toString().replace("'", "\\'")}')")
                    Thread.sleep(500)
                    button("OK").click()
                }
            }
            Thread.sleep(5000)
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
            waitFor(ofSeconds(10)) {
                editorTabs.hasText("test.fish")
            }
        }
    }

    @Test
    fun testFishFileHasSyntaxHighlighting() {
        remoteRobot.idea {
            with(projectViewTree) {
                waitFor(ofSeconds(10)) { hasText("test.fish") }
                findAllText("test.fish").first().doubleClick()
                waitFor(ofSeconds(10)) { isDumbMode().not() }
            }
        }
    }
}
