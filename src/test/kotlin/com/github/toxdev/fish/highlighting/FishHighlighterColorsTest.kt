package com.github.toxdev.fish.highlighting

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class FishHighlighterColorsTest {
    @Test
    fun `KEYWORD is not null`() {
        assertNotNull(FishHighlighterColors.KEYWORD)
    }

    @Test
    fun `KEYWORD_SUB is not null`() {
        assertNotNull(FishHighlighterColors.KEYWORD_SUB)
    }

    @Test
    fun `COMMENT is not null`() {
        assertNotNull(FishHighlighterColors.COMMENT)
    }

    @Test
    fun `SHEBANG is not null`() {
        assertNotNull(FishHighlighterColors.SHEBANG)
    }

    @Test
    fun `STRING is not null`() {
        assertNotNull(FishHighlighterColors.STRING)
    }

    @Test
    fun `ESCAPE is not null`() {
        assertNotNull(FishHighlighterColors.ESCAPE)
    }

    @Test
    fun `VARIABLE is not null`() {
        assertNotNull(FishHighlighterColors.VARIABLE)
    }

    @Test
    fun `NUMBER is not null`() {
        assertNotNull(FishHighlighterColors.NUMBER)
    }

    @Test
    fun `OPERATOR is not null`() {
        assertNotNull(FishHighlighterColors.OPERATOR)
    }

    @Test
    fun `REDIRECT is not null`() {
        assertNotNull(FishHighlighterColors.REDIRECT)
    }

    @Test
    fun `PARENTHESES is not null`() {
        assertNotNull(FishHighlighterColors.PARENTHESES)
    }

    @Test
    fun `BRACES is not null`() {
        assertNotNull(FishHighlighterColors.BRACES)
    }

    @Test
    fun `BRACKETS is not null`() {
        assertNotNull(FishHighlighterColors.BRACKETS)
    }

    @Test
    fun `COMMAND is not null`() {
        assertNotNull(FishHighlighterColors.COMMAND)
    }

    @Test
    fun `BAD_CHARACTER is not null`() {
        assertNotNull(FishHighlighterColors.BAD_CHARACTER)
    }
}
