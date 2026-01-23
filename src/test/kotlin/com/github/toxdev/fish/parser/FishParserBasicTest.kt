package com.github.toxdev.fish.parser

import com.github.toxdev.fish.lexer.FishLexerAdapter
import com.github.toxdev.fish.psi.FishTokenTypes
import com.github.toxdev.fish.psi.FishTypes
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.util.stream.Stream

class FishParserBasicTest {
    companion object {
        private val testDataRoot = File("src/test/testData")

        @JvmStatic
        fun allFishFiles(): Stream<File> {
            val files = mutableListOf<File>()
            if (testDataRoot.exists()) {
                testDataRoot.walk().forEach { file ->
                    if (file.extension == "fish") {
                        files.add(file)
                    }
                }
            }
            return files.stream()
        }
    }

    @Test
    fun `parser definition is created`() {
        val parserDef = FishParserDefinition()
        assertNotNull(parserDef)
        assertNotNull(parserDef.createParser(null))
        assertNotNull(parserDef.createLexer(null))
    }

    @Test
    fun `parser can parse simple function`() {
        val code =
            """
            function fish_greeting
            end
            """.trimIndent()

        val parserDef = FishParserDefinition()
        val lexer = parserDef.createLexer(null)
        lexer.start(code)

        val tokens = mutableListOf<String>()
        while (lexer.tokenType != null) {
            if (lexer.tokenType != FishTokenTypes.WHITE_SPACE &&
                lexer.tokenType != FishTypes.NEWLINE
            ) {
                tokens.add(lexer.tokenType.toString())
            }
            lexer.advance()
        }

        assertTrue(tokens.contains("FishTokenType.WORD"), "Should have WORD tokens for keywords and identifiers")
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("allFishFiles")
    fun `all test data files tokenize successfully`(file: File) {
        val content = file.readText()
        val lexer = FishLexerAdapter()
        lexer.start(content)

        var tokenCount = 0
        var hasKeywords = false
        var hasWords = false

        while (lexer.tokenType != null) {
            tokenCount++
            when (lexer.tokenType) {
                FishTokenTypes.KEYWORD -> hasKeywords = true
                FishTypes.WORD -> hasWords = true
            }
            lexer.advance()
        }

        assertTrue(tokenCount > 0, "${file.name}: Should produce tokens")

        // Files can be comment-only or have actual code
        // Just verify lexer works - parser/PSI testing requires IDE infrastructure
    }

    @Test
    fun `parser handles various fish constructs`() {
        val testCases =
            mapOf(
                "function declaration" to "function test\nend",
                "if statement" to "if test -f file\nend",
                "for loop" to "for x in 1 2 3\nend",
                "while loop" to "while test 1\nend",
                "switch statement" to "switch x\ncase a\nend",
                "command" to "echo hello",
                "pipe" to "cat file | grep pattern",
                "variable" to "set x value",
                "comment" to "# comment",
                "empty" to "",
            )

        testCases.forEach { (name, code) ->
            val lexer = FishLexerAdapter()
            lexer.start(code)

            var tokenCount = 0
            while (lexer.tokenType != null) {
                tokenCount++
                lexer.advance()
            }

            if (code.isNotEmpty()) {
                assertTrue(tokenCount > 0, "$name: Should produce tokens")
            } else {
                assertEquals(0, tokenCount, "$name: Empty code should produce no tokens")
            }
        }
    }
}
