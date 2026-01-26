package com.github.toxdev.fish.run

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FishRunConfigurationOptionsTest {
    @Test
    fun `scriptPath defaults to empty string`() {
        val options = FishRunConfigurationOptions()

        assertEquals("", options.scriptPath)
    }

    @Test
    fun `scriptPath can be set and retrieved`() {
        val options = FishRunConfigurationOptions()
        options.scriptPath = "/path/to/script.fish"

        assertEquals("/path/to/script.fish", options.scriptPath)
    }

    @Test
    fun `scriptArguments defaults to empty string`() {
        val options = FishRunConfigurationOptions()

        assertEquals("", options.scriptArguments)
    }

    @Test
    fun `scriptArguments can be set and retrieved`() {
        val options = FishRunConfigurationOptions()
        options.scriptArguments = "arg1 arg2"

        assertEquals("arg1 arg2", options.scriptArguments)
    }

    @Test
    fun `workingDirectory defaults to empty string`() {
        val options = FishRunConfigurationOptions()

        assertEquals("", options.workingDirectory)
    }

    @Test
    fun `workingDirectory can be set and retrieved`() {
        val options = FishRunConfigurationOptions()
        options.workingDirectory = "/home/user"

        assertEquals("/home/user", options.workingDirectory)
    }

    @Test
    fun `fishPath defaults to empty string`() {
        val options = FishRunConfigurationOptions()

        assertEquals("", options.fishPath)
    }

    @Test
    fun `fishPath can be set and retrieved`() {
        val options = FishRunConfigurationOptions()
        options.fishPath = "/usr/bin/fish"

        assertEquals("/usr/bin/fish", options.fishPath)
    }
}
