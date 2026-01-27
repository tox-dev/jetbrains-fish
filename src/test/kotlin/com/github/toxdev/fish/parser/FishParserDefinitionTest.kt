package com.github.toxdev.fish.parser

import com.github.toxdev.fish.lexer.FishLexerAdapter
import com.github.toxdev.fish.psi.FishTokenTypes
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FishParserDefinitionTest {
    private val parserDefinition = FishParserDefinition()

    @Test
    fun `createLexer returns FishLexerAdapter`() {
        val lexer = parserDefinition.createLexer(null)
        assertNotNull(lexer)
        assertTrue(lexer is FishLexerAdapter)
    }

    @Test
    fun `createParser returns FishParser`() {
        val parser = parserDefinition.createParser(null)
        assertNotNull(parser)
        assertTrue(parser is FishParser)
    }

    @Test
    fun `getFileNodeType returns FILE`() {
        val fileNodeType = parserDefinition.fileNodeType
        assertSame(FishParserDefinition.FILE, fileNodeType)
    }

    @Test
    fun `getCommentTokens returns COMMENTS TokenSet`() {
        val commentTokens = parserDefinition.commentTokens
        assertSame(FishTokenTypes.COMMENTS, commentTokens)
    }

    @Test
    fun `getStringLiteralElements returns STRINGS TokenSet`() {
        val stringTokens = parserDefinition.stringLiteralElements
        assertSame(FishTokenTypes.STRINGS, stringTokens)
    }

    @Test
    fun `FILE is not null`() {
        assertNotNull(FishParserDefinition.FILE)
    }
}
