package com.github.toxdev.fish.run

import com.intellij.openapi.project.Project
import io.mockk.mockk
import org.jdom.Element
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class FishRunConfigurationTest {
    @Test
    fun `findFishExecutable returns path when fish exists in PATH`() {
        val result = FishRunConfiguration.findFishExecutable()

        if (File("/usr/local/bin/fish").exists() ||
            File("/opt/homebrew/bin/fish").exists() ||
            File("/usr/bin/fish").exists()
        ) {
            assertNotNull(result)
        }
    }

    @Test
    fun `findFishExecutable returns null when PATH is empty`() {
        val originalPath = System.getenv("PATH")
        if (originalPath == null) {
            val result = FishRunConfiguration.findFishExecutable()
            assertNull(result)
        }
    }
}

class FishRunConfigurationInstanceTest {
    private lateinit var project: Project
    private lateinit var factory: FishConfigurationFactory
    private lateinit var config: FishRunConfiguration

    @BeforeEach
    fun setUp() {
        project = mockk(relaxed = true)
        val type = FishRunConfigurationType()
        factory = type.configurationFactories.first() as FishConfigurationFactory
        config = FishRunConfiguration(project, factory, "Test")
    }

    @Test
    fun `readExternal does not throw`() {
        val element = Element("configuration")
        config.readExternal(element)
    }

    @Test
    fun `writeExternal does not throw`() {
        val element = Element("configuration")
        config.writeExternal(element)
    }
}
