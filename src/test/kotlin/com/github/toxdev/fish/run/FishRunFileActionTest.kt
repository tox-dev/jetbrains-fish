package com.github.toxdev.fish.run

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class FishRunFileActionTest {
    private val action = FishRunFileAction()

    @Test
    fun `action is instantiable`() {
        assertNotNull(action)
    }

    @Test
    fun `ID constant is correct`() {
        assertEquals("runFishFileAction", FishRunFileAction.ID)
    }
}
