package com.github.toxdev.fish.inspection

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class FishUnusedVariableInspectionTest {
    private val inspection = FishUnusedVariableInspection()

    @Test
    fun `getDisplayName returns correct name`() {
        assertEquals("Unused local variable", inspection.displayName)
    }

    @Test
    fun `getShortName returns correct short name`() {
        assertEquals("FishUnusedVariable", inspection.shortName)
    }

    @Test
    fun `getGroupDisplayName returns Fish`() {
        assertEquals("Fish", inspection.groupDisplayName)
    }

    @Test
    fun `buildVisitor returns non-null visitor`() {
        val holder = io.mockk.mockk<com.intellij.codeInspection.ProblemsHolder>(relaxed = true)

        val visitor = inspection.buildVisitor(holder, true)

        assertNotNull(visitor)
    }

    @Test
    fun `buildVisitor returns non-null visitor for onTheFly false`() {
        val holder = io.mockk.mockk<com.intellij.codeInspection.ProblemsHolder>(relaxed = true)

        val visitor = inspection.buildVisitor(holder, false)

        assertNotNull(visitor)
    }
}
