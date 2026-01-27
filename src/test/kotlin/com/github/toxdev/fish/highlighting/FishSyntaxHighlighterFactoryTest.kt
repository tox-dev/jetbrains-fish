package com.github.toxdev.fish.highlighting

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FishSyntaxHighlighterFactoryTest {
    private val factory = FishSyntaxHighlighterFactory()

    @Test
    fun `getSyntaxHighlighter returns FishSyntaxHighlighter`() {
        val highlighter = factory.getSyntaxHighlighter(null, null)
        assertNotNull(highlighter)
        assertTrue(highlighter is FishSyntaxHighlighter)
    }
}
