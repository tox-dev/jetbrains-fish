package com.github.toxdev.fish.run

import com.intellij.execution.configurations.RunProfile
import com.intellij.execution.executors.DefaultRunExecutor
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FishRunProgramRunnerTest {
    private val runner = FishRunProgramRunner()

    @Test
    fun `runner is instantiable`() {
        assertNotNull(runner)
    }

    @Test
    fun `getRunnerId returns fishRunRunner`() {
        assertEquals("fishRunRunner", runner.runnerId)
    }

    @Test
    fun `canRun returns true for run executor and FishRunConfiguration`() {
        val config = mockk<FishRunConfiguration>()
        assertTrue(runner.canRun(DefaultRunExecutor.EXECUTOR_ID, config))
    }

    @Test
    fun `canRun returns false for non-run executor`() {
        val config = mockk<FishRunConfiguration>()
        assertFalse(runner.canRun("Debug", config))
    }

    @Test
    fun `canRun returns false for non-FishRunConfiguration`() {
        val config = mockk<RunProfile>()
        assertFalse(runner.canRun(DefaultRunExecutor.EXECUTOR_ID, config))
    }
}
