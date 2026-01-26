package com.github.toxdev.fish.highlighting

import com.github.toxdev.fish.psi.FishTokenTypes
import com.github.toxdev.fish.psi.FishTypes
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FishSyntaxHighlighterTest {
    private val highlighter = FishSyntaxHighlighter()

    @Test
    fun `test highlighter returns highlighting lexer`() {
        val lexer = highlighter.highlightingLexer
        assertNotNull(lexer)
        assertTrue(lexer is FishHighlightingLexer)
    }

    @Test
    fun `test keyword highlighting`() {
        val attrs = highlighter.getTokenHighlights(FishTokenTypes.KEYWORD)
        assertEquals(1, attrs.size)
        assertEquals(FishHighlighterColors.KEYWORD, attrs[0])
    }

    @Test
    fun `test comment highlighting`() {
        val attrs = highlighter.getTokenHighlights(FishTypes.COMMENT)
        assertEquals(1, attrs.size)
        assertEquals(FishHighlighterColors.COMMENT, attrs[0])
    }

    @Test
    fun `test shebang highlighting`() {
        val attrs = highlighter.getTokenHighlights(FishTypes.SHEBANG)
        assertEquals(1, attrs.size)
        assertEquals(FishHighlighterColors.SHEBANG, attrs[0])
    }

    @Test
    fun `test string quote highlighting`() {
        val singleQuoteAttrs = highlighter.getTokenHighlights(FishTypes.SINGLE_QUOTE)
        assertEquals(1, singleQuoteAttrs.size)
        assertEquals(FishHighlighterColors.STRING, singleQuoteAttrs[0])

        val doubleQuoteAttrs = highlighter.getTokenHighlights(FishTypes.DOUBLE_QUOTE)
        assertEquals(1, doubleQuoteAttrs.size)
        assertEquals(FishHighlighterColors.STRING, doubleQuoteAttrs[0])
    }

    @Test
    fun `test string content highlighting`() {
        val attrs = highlighter.getTokenHighlights(FishTypes.STRING_CONTENT)
        assertEquals(1, attrs.size)
        assertEquals(FishHighlighterColors.STRING, attrs[0])
    }

    @Test
    fun `test escape sequence highlighting`() {
        val attrs = highlighter.getTokenHighlights(FishTypes.ESCAPE)
        assertEquals(1, attrs.size)
        assertEquals(FishHighlighterColors.ESCAPE, attrs[0])
    }

    @Test
    fun `test variable highlighting`() {
        val attrs = highlighter.getTokenHighlights(FishTypes.VARIABLE)
        assertEquals(1, attrs.size)
        assertEquals(FishHighlighterColors.VARIABLE, attrs[0])
    }

    @Test
    fun `test number highlighting`() {
        val attrs = highlighter.getTokenHighlights(FishTypes.NUMBER)
        assertEquals(1, attrs.size)
        assertEquals(FishHighlighterColors.NUMBER, attrs[0])
    }

    @Test
    fun `test operator highlighting`() {
        val pipeAttrs = highlighter.getTokenHighlights(FishTypes.PIPE)
        assertEquals(1, pipeAttrs.size)
        assertEquals(FishHighlighterColors.OPERATOR, pipeAttrs[0])

        val andAttrs = highlighter.getTokenHighlights(FishTypes.AND_AND)
        assertEquals(1, andAttrs.size)
        assertEquals(FishHighlighterColors.OPERATOR, andAttrs[0])

        val orAttrs = highlighter.getTokenHighlights(FishTypes.OR_OR)
        assertEquals(1, orAttrs.size)
        assertEquals(FishHighlighterColors.OPERATOR, orAttrs[0])
    }

    @Test
    fun `test redirect highlighting`() {
        val attrs = highlighter.getTokenHighlights(FishTypes.REDIRECT)
        assertEquals(1, attrs.size)
        assertEquals(FishHighlighterColors.REDIRECT, attrs[0])
    }

    @Test
    fun `test semicolon highlighting`() {
        val attrs = highlighter.getTokenHighlights(FishTypes.SEMICOLON)
        assertEquals(1, attrs.size)
        assertEquals(FishHighlighterColors.OPERATOR, attrs[0])
    }

    @Test
    fun `test bracket highlighting`() {
        val lparenAttrs = highlighter.getTokenHighlights(FishTypes.LPAREN)
        assertEquals(1, lparenAttrs.size)
        assertEquals(FishHighlighterColors.PARENTHESES, lparenAttrs[0])

        val rparenAttrs = highlighter.getTokenHighlights(FishTypes.RPAREN)
        assertEquals(1, rparenAttrs.size)
        assertEquals(FishHighlighterColors.PARENTHESES, rparenAttrs[0])

        val lbraceAttrs = highlighter.getTokenHighlights(FishTypes.LBRACE)
        assertEquals(1, lbraceAttrs.size)
        assertEquals(FishHighlighterColors.BRACES, lbraceAttrs[0])

        val rbraceAttrs = highlighter.getTokenHighlights(FishTypes.RBRACE)
        assertEquals(1, rbraceAttrs.size)
        assertEquals(FishHighlighterColors.BRACES, rbraceAttrs[0])

        val lbracketAttrs = highlighter.getTokenHighlights(FishTypes.LBRACKET)
        assertEquals(1, lbracketAttrs.size)
        assertEquals(FishHighlighterColors.BRACKETS, lbracketAttrs[0])

        val rbracketAttrs = highlighter.getTokenHighlights(FishTypes.RBRACKET)
        assertEquals(1, rbracketAttrs.size)
        assertEquals(FishHighlighterColors.BRACKETS, rbracketAttrs[0])
    }

    @Test
    fun `test bad character highlighting`() {
        val attrs = highlighter.getTokenHighlights(FishTokenTypes.BAD_CHARACTER)
        assertEquals(1, attrs.size)
        assertEquals(FishHighlighterColors.BAD_CHARACTER, attrs[0])
    }

    @Test
    fun `test highlighter colors have fallbacks`() {
        assertNotNull(FishHighlighterColors.KEYWORD.fallbackAttributeKey)
        assertEquals(DefaultLanguageHighlighterColors.KEYWORD, FishHighlighterColors.KEYWORD.fallbackAttributeKey)

        assertNotNull(FishHighlighterColors.COMMENT.fallbackAttributeKey)
        assertEquals(DefaultLanguageHighlighterColors.LINE_COMMENT, FishHighlighterColors.COMMENT.fallbackAttributeKey)

        assertNotNull(FishHighlighterColors.STRING.fallbackAttributeKey)
        assertEquals(DefaultLanguageHighlighterColors.STRING, FishHighlighterColors.STRING.fallbackAttributeKey)

        assertNotNull(FishHighlighterColors.NUMBER.fallbackAttributeKey)
        assertEquals(DefaultLanguageHighlighterColors.NUMBER, FishHighlighterColors.NUMBER.fallbackAttributeKey)

        assertNotNull(FishHighlighterColors.OPERATOR.fallbackAttributeKey)
        assertEquals(DefaultLanguageHighlighterColors.OPERATION_SIGN, FishHighlighterColors.OPERATOR.fallbackAttributeKey)
    }

    @Test
    fun `test color settings page configuration`() {
        val colorSettingsPage = FishColorSettingsPage()

        assertNotNull(colorSettingsPage.highlighter)
        assertTrue(colorSettingsPage.highlighter is FishSyntaxHighlighter)

        assertNotNull(colorSettingsPage.demoText)
        assertTrue(colorSettingsPage.demoText.isNotEmpty())

        val descriptors = colorSettingsPage.attributeDescriptors
        assertNotNull(descriptors)
        assertTrue(descriptors.isNotEmpty())

        val descriptorNames = descriptors.map { it.displayName }.toSet()
        assertTrue(descriptorNames.contains("Keyword"))
        assertTrue(descriptorNames.contains("Comment"))
        assertTrue(descriptorNames.contains("String"))
        assertTrue(descriptorNames.contains("Number"))
        assertTrue(descriptorNames.contains("Variable"))
    }

    @Test
    fun `test demo text contains all syntax elements`() {
        val colorSettingsPage = FishColorSettingsPage()
        val demoText = colorSettingsPage.demoText

        assertTrue(demoText.contains("#!/"), "Should contain shebang")
        assertTrue(demoText.contains("#"), "Should contain comment")
        assertTrue(demoText.contains("function"), "Should contain function keyword")
        assertTrue(demoText.contains("if"), "Should contain if keyword")
        assertTrue(demoText.contains("for"), "Should contain for keyword")
        assertTrue(demoText.contains("\""), "Should contain double quotes")
        assertTrue(demoText.contains("'"), "Should contain single quotes")
        assertTrue(demoText.contains("$"), "Should contain variables")
        assertTrue(demoText.contains("|"), "Should contain pipe")
        assertTrue(demoText.contains(">"), "Should contain redirection")
    }

    @Test
    fun `test highlighting of real fish code`() {
        val code =
            """
            #!/usr/bin/env fish
            # Test function
            function greet --description 'Say hello'
                set name ${'$'}1
                echo "Hello, ${'$'}name!"
                return 0
            end
            """.trimIndent()

        val lexer = FishHighlightingLexer()
        lexer.start(code, 0, code.length, 0)

        val tokenTypes = mutableListOf<String>()
        while (lexer.tokenType != null) {
            tokenTypes.add(lexer.tokenType.toString())
            lexer.advance()
        }

        assertTrue(tokenTypes.contains("FishTokenType.SHEBANG"))
        assertTrue(tokenTypes.contains("FishTokenType.COMMENT"))
        assertTrue(tokenTypes.contains("FishTokenType.KEYWORD"))
        assertTrue(tokenTypes.contains("FishTokenType.STRING_CONTENT"))
        assertTrue(tokenTypes.contains("FishTokenType.VARIABLE"))
    }

    @Test
    fun `test all token types have highlighting`() {
        val tokenTypes =
            listOf(
                FishTokenTypes.KEYWORD,
                FishTypes.COMMENT,
                FishTypes.SHEBANG,
                FishTypes.STRING_CONTENT,
                FishTypes.SINGLE_QUOTE,
                FishTypes.DOUBLE_QUOTE,
                FishTypes.ESCAPE,
                FishTypes.VARIABLE,
                FishTypes.NUMBER,
                FishTypes.PIPE,
                FishTypes.AND_AND,
                FishTypes.OR_OR,
                FishTypes.BACKGROUND,
                FishTypes.REDIRECT,
                FishTypes.SEMICOLON,
                FishTypes.LPAREN,
                FishTypes.RPAREN,
                FishTypes.LBRACE,
                FishTypes.RBRACE,
                FishTypes.LBRACKET,
                FishTypes.RBRACKET,
                FishTypes.WORD,
                FishTokenTypes.BAD_CHARACTER,
            )

        tokenTypes.forEach { tokenType ->
            val attrs = highlighter.getTokenHighlights(tokenType)
            assertNotNull(attrs, "Token type $tokenType should have highlighting")
            assertTrue(attrs.isNotEmpty(), "Token type $tokenType should have at least one attribute")
        }
    }

    @Test
    fun `test highlighting lexer returns KEYWORD for fish keywords`() {
        val lexer = FishHighlightingLexer()
        lexer.start("function if else for while end", 0, 30, 0)

        val tokens = mutableListOf<Pair<String, String>>()
        while (lexer.tokenType != null) {
            if (lexer.tokenType != FishTokenTypes.WHITE_SPACE) {
                tokens.add(lexer.tokenType.toString() to lexer.tokenText)
            }
            lexer.advance()
        }

        assertTrue(tokens.all { it.first == "FishTokenType.KEYWORD" }, "All keywords should be KEYWORD token type")
    }
}
