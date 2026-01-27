package com.github.toxdev.fish.lexer

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class FishLexerAdapterTest {
    @Test
    fun `lexer can be instantiated`() {
        val lexer = FishLexerAdapter()
        assertNotNull(lexer)
    }
}
