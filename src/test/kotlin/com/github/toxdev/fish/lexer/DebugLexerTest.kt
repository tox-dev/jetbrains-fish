package com.github.toxdev.fish.lexer

import org.junit.jupiter.api.Test
import java.io.File

class DebugLexerTest {
    @Test
    fun `debug tmux transient prompt multiline strings`() {
        val content = File("src/test/testData/edge_cases/tmux-transient-prompt.fish").readText()
        val lexer = FishLexerAdapter()
        lexer.start(content)

        var inSingleQuote = false
        var singleQuoteCount = 0
        val issues = mutableListOf<String>()

        while (lexer.tokenType != null) {
            val tokenText = lexer.tokenText
            val tokenType = lexer.tokenType.toString()

            if (tokenType.contains("SINGLE_QUOTE")) {
                singleQuoteCount++
                inSingleQuote = !inSingleQuote
                val state = if (inSingleQuote) "OPEN" else "CLOSE"
                println("Quote #$singleQuoteCount ($state) at pos ${lexer.tokenStart}: '$tokenText'")
            }

            if (tokenType.contains("BAD_CHARACTER")) {
                issues.add("BAD at ${lexer.tokenStart}: '${tokenText.replace("\n", "\\n")}'")
            }

            lexer.advance()
        }

        println("\nTotal single quotes: $singleQuoteCount")
        if (singleQuoteCount % 2 != 0) {
            println("WARNING: Odd number of single quotes - unbalanced!")
        }

        if (issues.isNotEmpty()) {
            println("\nBad characters found:")
            issues.forEach { println("  $it") }
        }
    }
}
