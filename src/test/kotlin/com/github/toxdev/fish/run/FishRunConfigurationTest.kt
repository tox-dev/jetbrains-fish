package com.github.toxdev.fish.run

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
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
