package com.github.toxdev.fish

import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FishBraceMatcherTest {
    private val matcher = FishBraceMatcher()

    @Test
    fun `getPairs returns three brace pairs`() {
        val pairs = matcher.pairs
        assertEquals(3, pairs.size)
    }

    @Test
    fun `isPairedBracesAllowedBeforeType returns true`() {
        assertTrue(matcher.isPairedBracesAllowedBeforeType(mockk(), mockk()))
    }

    @Test
    fun `isPairedBracesAllowedBeforeType returns true for null context`() {
        assertTrue(matcher.isPairedBracesAllowedBeforeType(mockk(), null))
    }

    @Test
    fun `getCodeConstructStart returns same offset`() {
        assertEquals(42, matcher.getCodeConstructStart(mockk(), 42))
    }

    @Test
    fun `getCodeConstructStart returns offset for null file`() {
        assertEquals(0, matcher.getCodeConstructStart(null, 0))
    }
}
