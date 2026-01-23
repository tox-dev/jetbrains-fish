package com.github.toxdev.fish.highlighting

import com.github.toxdev.fish.psi.FishTokenTypes
import com.github.toxdev.fish.psi.FishTypes
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.util.stream.Stream

/**
 * Integration tests for Fish shell syntax highlighting on real files.
 */
class FishHighlightingIntegrationTest {
    companion object {
        private val testDataRoot = File("src/test/testData")

        @JvmStatic
        fun highlightingTestFiles(): Stream<File> =
            if (testDataRoot.resolve("highlighting").exists()) {
                testDataRoot
                    .resolve("highlighting")
                    .listFiles()
                    ?.filter { it.extension == "fish" }
                    ?.stream() ?: Stream.empty()
            } else {
                Stream.empty()
            }
    }

    private val highlighter = FishSyntaxHighlighter()

    @ParameterizedTest(name = "{0}")
    @MethodSource("highlightingTestFiles")
    fun `highlighting test file contains expected token types`(file: File) {
        val content = file.readText()
        val lexer = FishHighlightingLexer()
        lexer.start(content, 0, content.length, 0)

        val tokenCounts = mutableMapOf<String, Int>()
        val tokenPositions = mutableMapOf<String, MutableList<Int>>()

        while (lexer.tokenType != null) {
            val tokenTypeName = lexer.tokenType.toString()
            tokenCounts[tokenTypeName] = tokenCounts.getOrDefault(tokenTypeName, 0) + 1
            tokenPositions.getOrPut(tokenTypeName) { mutableListOf() }.add(lexer.tokenStart)

            if (lexer.tokenType != FishTypes.NEWLINE && lexer.tokenType != FishTokenTypes.WHITE_SPACE) {
                val attrs = highlighter.getTokenHighlights(lexer.tokenType!!)
                assertNotNull(attrs, "Token type $tokenTypeName at position ${lexer.tokenStart} should have highlighting")
                assertTrue(attrs.isNotEmpty(), "Token type $tokenTypeName should have highlighting attributes")
            }

            lexer.advance()
        }

        assertTrue(tokenCounts.isNotEmpty(), "${file.name}: Should produce tokens")

        if (file.name == "basic.fish") {
            assertTrue(
                tokenCounts.containsKey("FishTokenType.SHEBANG"),
                "basic.fish should contain shebang",
            )
            assertTrue(
                tokenCounts.containsKey("FishTokenType.COMMENT"),
                "basic.fish should contain comments",
            )
            assertTrue(
                tokenCounts.containsKey("FishTokenType.KEYWORD"),
                "basic.fish should contain keywords",
            )
            assertTrue(
                tokenCounts.containsKey("FishTokenType.STRING_CONTENT"),
                "basic.fish should contain strings",
            )
            assertTrue(
                tokenCounts.containsKey("FishTokenType.VARIABLE"),
                "basic.fish should contain variables",
            )
            assertTrue(
                tokenCounts.containsKey("FishTokenType.PIPE"),
                "basic.fish should contain pipes",
            )
            assertTrue(
                tokenCounts.containsKey("FishTokenType.REDIRECT"),
                "basic.fish should contain redirections",
            )

            println("${file.name} token distribution:")
            tokenCounts.toSortedMap().forEach { (type, count) ->
                println("  $type: $count occurrences")
            }
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("highlightingTestFiles")
    fun `all tokens have consistent highlighting attributes`(file: File) {
        val content = file.readText()
        val lexer = FishHighlightingLexer()
        lexer.start(content, 0, content.length, 0)

        val tokenTypeToAttrs = mutableMapOf<String, Set<String>>()

        while (lexer.tokenType != null) {
            val tokenTypeName = lexer.tokenType.toString()
            val attrs = highlighter.getTokenHighlights(lexer.tokenType!!)
            val attrNames = attrs.map { it.externalName }.toSet()

            if (tokenTypeToAttrs.containsKey(tokenTypeName)) {
                assertEquals(
                    tokenTypeToAttrs[tokenTypeName],
                    attrNames,
                    "Token type $tokenTypeName should have consistent highlighting attributes",
                )
            } else {
                tokenTypeToAttrs[tokenTypeName] = attrNames
            }

            lexer.advance()
        }
    }

    private fun assertEquals(
        expected: Set<String>?,
        actual: Set<String>,
        message: String,
    ) {
        if (expected != actual) {
            throw AssertionError("$message: expected=$expected, actual=$actual")
        }
    }
}
