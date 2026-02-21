package com.github.toxdev.fish.lexer

import com.github.toxdev.fish.highlighting.FishHighlightingLexer
import com.github.toxdev.fish.psi.FishTokenTypes
import com.github.toxdev.fish.psi.FishTypes
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.util.stream.Stream

/**
 * Lexer tests for Fish shell language.
 */
class FishLexerTest {
    companion object {
        private val testDataRoot = File("src/test/testData")

        @JvmStatic
        fun canonicalFunctionFiles(): Stream<File> = filesInDirectory(File(testDataRoot, "canonical/functions"))

        @JvmStatic
        fun canonicalCompletionFiles(): Stream<File> = filesInDirectory(File(testDataRoot, "canonical/completions"))

        @JvmStatic
        fun edgeCaseFiles(): Stream<File> = filesInDirectory(File(testDataRoot, "edge_cases"))

        @JvmStatic
        fun promptFiles(): Stream<File> = filesInDirectory(File(testDataRoot, "prompts"))

        @JvmStatic
        fun highlightingFiles(): Stream<File> = filesInDirectory(File(testDataRoot, "highlighting"))

        @JvmStatic
        fun sampleFiles(): Stream<File> = filesInDirectory(File(testDataRoot, "samples"))

        private fun filesInDirectory(dir: File): Stream<File> =
            if (dir.exists()) {
                dir.listFiles()?.filter { it.extension == "fish" }?.stream() ?: Stream.empty()
            } else {
                Stream.empty()
            }
    }

    @Test
    fun `test keywords`() {
        val input = "if test -f file.fish and not false then echo yes end"
        val lexer = FishHighlightingLexer()
        lexer.start(input, 0, input.length, 0)

        val tokens = mutableListOf<String>()
        while (lexer.tokenType != null) {
            tokens.add(lexer.tokenType.toString())
            lexer.advance()
        }

        assert(tokens.contains("FishTokenType.KEYWORD")) { "Expected to find KEYWORD token in: $tokens" }
    }

    @Test
    fun `test shebang`() {
        val input = "#!/usr/bin/env fish"
        val lexer = FishLexerAdapter()
        lexer.start(input)

        assertEquals(FishTypes.SHEBANG, lexer.tokenType)
    }

    @Test
    fun `test comments`() {
        val input = "# This is a comment"
        val lexer = FishLexerAdapter()
        lexer.start(input)

        assertEquals(FishTypes.COMMENT, lexer.tokenType)
    }

    @Test
    fun `test strings`() {
        val input = "'single' \"double\""
        val lexer = FishLexerAdapter()
        lexer.start(input)

        assertEquals(FishTypes.SINGLE_QUOTE, lexer.tokenType)
    }

    @Test
    fun `test variables`() {
        val input = "\$greeting"
        val lexer = FishLexerAdapter()
        lexer.start(input)

        assertEquals(FishTypes.VARIABLE, lexer.tokenType)
    }

    @Test
    fun `test operators`() {
        val input = "&&"
        val lexer = FishLexerAdapter()
        lexer.start(input)

        assertEquals(FishTypes.AND_AND, lexer.tokenType)
    }

    @Test
    fun `test pipe`() {
        val input = "|"
        val lexer = FishLexerAdapter()
        lexer.start(input)

        assertEquals(FishTypes.PIPE, lexer.tokenType)
    }

    @Test
    fun `test brace expansion in word`() {
        val input = "bgp_color_{pwd,git,error}"
        val lexer = FishLexerAdapter()
        lexer.start(input)

        assertEquals(FishTypes.WORD, lexer.tokenType, "Brace expansion should be part of WORD")
        assertEquals(input, lexer.tokenText, "Entire brace expansion pattern should be single token")
        lexer.advance()
        assertEquals(null, lexer.tokenType, "Should have no more tokens")
    }

    @Test
    fun `test standalone braces are separate tokens`() {
        val input = "{a,b}"
        val lexer = FishLexerAdapter()
        lexer.start(input)

        assertEquals(FishTypes.LBRACE, lexer.tokenType, "Standalone { should be LBRACE")
    }

    @Test
    fun `test command substitution`() {
        val input = "(math 1 + 2)"
        val lexer = FishLexerAdapter()
        lexer.start(input)

        assertEquals(FishTypes.LPAREN, lexer.tokenType)
        lexer.advance()

        while (lexer.tokenType == FishTokenTypes.WHITE_SPACE) {
            lexer.advance()
        }

        assertEquals(FishTypes.WORD, lexer.tokenType)
    }

    @Test
    fun `test config file tokenizes`() {
        val configFile = File(testDataRoot, "canonical/conf.d/config.fish")
        if (configFile.exists()) {
            testFileTokenizes(configFile)
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("canonicalFunctionFiles")
    fun `canonical function file tokenizes without errors`(file: File) {
        testFileTokenizes(file)
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("canonicalCompletionFiles")
    fun `canonical completion file tokenizes without errors`(file: File) {
        testFileTokenizes(file)
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("edgeCaseFiles")
    fun `edge case file tokenizes without errors`(file: File) {
        testFileTokenizes(file)
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("promptFiles")
    fun `prompt file tokenizes without errors`(file: File) {
        testFileTokenizes(file)
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("highlightingFiles")
    fun `highlighting file tokenizes without errors`(file: File) {
        testFileTokenizes(file)
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("sampleFiles")
    fun `sample file tokenizes without errors`(file: File) {
        testFileTokenizes(file)
    }

    @Test
    fun `test multiline string tokenization`() {
        val input = "'hello\nworld'"
        val lexer = FishLexerAdapter()
        lexer.start(input)

        val tokens = mutableListOf<String>()
        while (lexer.tokenType != null) {
            tokens.add("${lexer.tokenType}:'${lexer.tokenText.replace("\n", "\\n")}'")
            lexer.advance()
        }

        println("Tokens for multiline string: $tokens")

        assert(tokens.size == 5) { "Expected 5 tokens, got ${tokens.size}: $tokens" }
        assert(tokens[0].contains("SINGLE_QUOTE")) { "First token should be SINGLE_QUOTE: ${tokens[0]}" }
        assert(tokens[1].contains("STRING_CONTENT")) { "Second token should be STRING_CONTENT: ${tokens[1]}" }
        assert(tokens[2].contains("STRING_CONTENT")) { "Third token should be STRING_CONTENT (newline): ${tokens[2]}" }
        assert(tokens[3].contains("STRING_CONTENT")) { "Fourth token should be STRING_CONTENT: ${tokens[3]}" }
        assert(tokens[4].contains("SINGLE_QUOTE")) { "Fifth token should be SINGLE_QUOTE: ${tokens[4]}" }
    }

    @Test
    fun `test escaped backslashes before escaped single quote`() {
        val input = "echo '\\\\\\''"
        val lexer = FishLexerAdapter()
        lexer.start(input)

        val tokens = collectTokens(lexer)

        assertEquals("FishTokenType.WORD", tokens[0].first, "echo")
        assertEquals("WHITE_SPACE", tokens[1].first)
        assertEquals("FishTokenType.SINGLE_QUOTE", tokens[2].first, "opening quote")
        assertEquals("FishTokenType.ESCAPE", tokens[3].first, "escaped backslash \\\\")
        assertEquals("FishTokenType.ESCAPE", tokens[4].first, "escaped single quote \\'")
        assertEquals("FishTokenType.SINGLE_QUOTE", tokens[5].first, "closing quote")
        assertEquals(6, tokens.size)
    }

    @Test
    fun `test multiple escaped backslashes in single quoted string`() {
        val input = "'\\\\\\\\'"
        val lexer = FishLexerAdapter()
        lexer.start(input)

        val tokens = collectTokens(lexer)

        assertEquals("FishTokenType.SINGLE_QUOTE", tokens[0].first, "opening quote")
        assertEquals("FishTokenType.ESCAPE", tokens[1].first, "first escaped backslash")
        assertEquals("FishTokenType.ESCAPE", tokens[2].first, "second escaped backslash")
        assertEquals("FishTokenType.SINGLE_QUOTE", tokens[3].first, "closing quote")
        assertEquals(4, tokens.size)
    }

    @Test
    fun `test lone backslash in single quoted string`() {
        val input = "'a\\b'"
        val lexer = FishLexerAdapter()
        lexer.start(input)

        val tokens = collectTokens(lexer)

        assertEquals("FishTokenType.SINGLE_QUOTE", tokens[0].first, "opening quote")
        assertEquals("FishTokenType.STRING_CONTENT", tokens[1].first, "a")
        assertEquals("FishTokenType.STRING_CONTENT", tokens[2].first, "lone backslash")
        assertEquals("FishTokenType.STRING_CONTENT", tokens[3].first, "b")
        assertEquals("FishTokenType.SINGLE_QUOTE", tokens[4].first, "closing quote")
        assertEquals(5, tokens.size)
    }

    private fun collectTokens(lexer: FishLexerAdapter): List<Pair<String, String>> {
        val tokens = mutableListOf<Pair<String, String>>()
        while (lexer.tokenType != null) {
            tokens.add(lexer.tokenType.toString() to lexer.tokenText)
            lexer.advance()
        }
        return tokens
    }

    @Test
    fun `analyze bad characters`() {
        val badChars = mutableMapOf<Char, MutableList<String>>()

        fun analyzeFile(file: File) {
            val lexer = FishLexerAdapter()
            val content = file.readText()
            lexer.start(content)

            while (lexer.tokenType != null) {
                if (lexer.tokenType.toString().contains("BAD_CHARACTER")) {
                    val badText = lexer.tokenText
                    if (badText.isNotEmpty()) {
                        val char = badText[0]
                        badChars.getOrPut(char) { mutableListOf() }.add(file.name)
                    }
                }
                lexer.advance()
            }
        }

        fun analyzeDirectory(dir: File) {
            dir.listFiles()?.forEach { file ->
                when {
                    file.isDirectory -> analyzeDirectory(file)
                    file.extension == "fish" -> analyzeFile(file)
                }
            }
        }

        analyzeDirectory(testDataRoot)

        if (badChars.isNotEmpty()) {
            println("\nBad characters found:")
            badChars.toSortedMap().forEach { (char, files) ->
                println(
                    "  '$char' (\\u${
                        char.code.toString(16).padStart(
                            4,
                            '0',
                        )
                    }): ${files.distinct().size} files",
                )
            }
        }
    }

    private fun testFileTokenizes(file: File) {
        val lexer = FishLexerAdapter()
        val content = file.readText()
        lexer.start(content)

        var tokenCount = 0
        val badTokens = mutableListOf<String>()

        while (lexer.tokenType != null) {
            assertNotNull(lexer.tokenType, "Token type should not be null in ${file.name}")

            if (lexer.tokenType.toString().contains("BAD_CHARACTER")) {
                badTokens.add("'${lexer.tokenText}' at position ${lexer.tokenStart}")
            }

            tokenCount++
            lexer.advance()
        }

        assert(tokenCount > 0) { "${file.name}: Should produce at least one token" }
        assert(badTokens.isEmpty()) {
            "${file.name}: Found BAD_CHARACTER tokens: ${badTokens.joinToString(", ")}"
        }
    }
}
