package com.github.toxdev.fish.inspection

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class FishDeprecatedSyntaxInspectionTest {
    private val inspection = FishDeprecatedSyntaxInspection()

    @Test
    fun `getDisplayName returns correct name`() {
        assertEquals("Deprecated Fish syntax", inspection.displayName)
    }

    @Test
    fun `getShortName returns correct short name`() {
        assertEquals("FishDeprecatedSyntax", inspection.shortName)
    }

    @Test
    fun `getGroupDisplayName returns Fish`() {
        assertEquals("Fish", inspection.groupDisplayName)
    }

    @Test
    fun `deprecated functions map contains expected entries`() {
        val functions = FishDeprecatedSyntaxInspection.DEPRECATED_FUNCTIONS

        assertEquals("fish_git_prompt", functions["__fish_git_prompt"])
        assertEquals("fish_vcs_prompt", functions["__fish_vcs_prompt"])
        assertEquals("fish_hg_prompt", functions["__fish_hg_prompt"])
        assertEquals("fish_svn_prompt", functions["__fish_svn_prompt"])
        assertEquals("prompt_pwd", functions["__fish_pwd"])
        assertEquals("fish_commandline_prepend sudo", functions["__fish_prepend_sudo"])
        assertEquals("string (no localization needed)", functions["N_"])
    }

    @Test
    fun `deprecated history flags map contains expected entries`() {
        val flags = FishDeprecatedSyntaxInspection.DEPRECATED_HISTORY_FLAGS

        assertEquals("history search", flags["--search"])
        assertEquals("history search", flags["-S"])
        assertEquals("history delete", flags["--delete"])
        assertEquals("history delete", flags["-D"])
        assertEquals("history save", flags["--save"])
        assertEquals("history save", flags["-V"])
        assertEquals("history clear", flags["--clear"])
        assertEquals("history clear", flags["-X"])
        assertEquals("history merge", flags["--merge"])
        assertEquals("history merge", flags["-M"])
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
