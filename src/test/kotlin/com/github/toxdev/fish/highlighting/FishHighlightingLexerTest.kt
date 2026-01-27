package com.github.toxdev.fish.highlighting

import com.github.toxdev.fish.psi.FishTokenTypes
import com.github.toxdev.fish.psi.FishTypes
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class FishHighlightingLexerTest {
    @Test
    fun `lexer can be instantiated`() {
        val lexer = FishHighlightingLexer()
        assertNotNull(lexer)
    }

    @Test
    fun `lexer returns KEYWORD for function keyword`() {
        val lexer = FishHighlightingLexer()
        lexer.start("function", 0, 8, 0)
        assertEquals(FishTokenTypes.KEYWORD, lexer.tokenType)
    }

    @Test
    fun `lexer returns KEYWORD for if keyword`() {
        val lexer = FishHighlightingLexer()
        lexer.start("if", 0, 2, 0)
        assertEquals(FishTokenTypes.KEYWORD, lexer.tokenType)
    }

    @Test
    fun `lexer returns KEYWORD for end keyword`() {
        val lexer = FishHighlightingLexer()
        lexer.start("end", 0, 3, 0)
        assertEquals(FishTokenTypes.KEYWORD, lexer.tokenType)
    }

    @Test
    fun `lexer returns WORD for non-keyword word`() {
        val lexer = FishHighlightingLexer()
        lexer.start("echo", 0, 4, 0)
        assertEquals(FishTypes.WORD, lexer.tokenType)
    }

    @Test
    fun `getState returns current state`() {
        val lexer = FishHighlightingLexer()
        lexer.start("function", 0, 8, 0)
        assertNotNull(lexer.state)
    }

    @Test
    fun `getTokenStart returns token start position`() {
        val lexer = FishHighlightingLexer()
        lexer.start("function", 0, 8, 0)
        assertEquals(0, lexer.tokenStart)
    }

    @Test
    fun `getTokenEnd returns token end position`() {
        val lexer = FishHighlightingLexer()
        lexer.start("function", 0, 8, 0)
        assertEquals(8, lexer.tokenEnd)
    }

    @Test
    fun `advance moves to next token`() {
        val lexer = FishHighlightingLexer()
        lexer.start("if end", 0, 6, 0)
        assertEquals(FishTokenTypes.KEYWORD, lexer.tokenType)
        lexer.advance()
        lexer.advance()
        assertEquals(FishTokenTypes.KEYWORD, lexer.tokenType)
    }

    @Test
    fun `getBufferSequence returns input buffer`() {
        val lexer = FishHighlightingLexer()
        lexer.start("test", 0, 4, 0)
        assertEquals("test", lexer.bufferSequence.toString())
    }

    @Test
    fun `getBufferEnd returns buffer end position`() {
        val lexer = FishHighlightingLexer()
        lexer.start("test", 0, 4, 0)
        assertEquals(4, lexer.bufferEnd)
    }

    @Test
    fun `getTokenType returns null at end of input`() {
        val lexer = FishHighlightingLexer()
        lexer.start("x", 0, 1, 0)
        lexer.advance()
        assertNull(lexer.tokenType)
    }
}
