package com.github.toxdev.fish.psi

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FishTokenTypesTest {
    @Test
    fun `WHITE_SPACE is not null`() {
        assertNotNull(FishTokenTypes.WHITE_SPACE)
    }

    @Test
    fun `BAD_CHARACTER is not null`() {
        assertNotNull(FishTokenTypes.BAD_CHARACTER)
    }

    @Test
    fun `KEYWORD is not null`() {
        assertNotNull(FishTokenTypes.KEYWORD)
    }

    @Test
    fun `COMMENTS contains COMMENT and SHEBANG`() {
        assertTrue(FishTokenTypes.COMMENTS.contains(FishTypes.COMMENT))
        assertTrue(FishTokenTypes.COMMENTS.contains(FishTypes.SHEBANG))
    }

    @Test
    fun `STRINGS contains quote types`() {
        assertTrue(FishTokenTypes.STRINGS.contains(FishTypes.SINGLE_QUOTE))
        assertTrue(FishTokenTypes.STRINGS.contains(FishTypes.DOUBLE_QUOTE))
        assertTrue(FishTokenTypes.STRINGS.contains(FishTypes.STRING_CONTENT))
    }

    @Test
    fun `KEYWORDS contains KEYWORD`() {
        assertTrue(FishTokenTypes.KEYWORDS.contains(FishTokenTypes.KEYWORD))
    }

    @Test
    fun `BRACES contains all bracket types`() {
        assertTrue(FishTokenTypes.BRACES.contains(FishTypes.LPAREN))
        assertTrue(FishTokenTypes.BRACES.contains(FishTypes.RPAREN))
        assertTrue(FishTokenTypes.BRACES.contains(FishTypes.LBRACE))
        assertTrue(FishTokenTypes.BRACES.contains(FishTypes.RBRACE))
        assertTrue(FishTokenTypes.BRACES.contains(FishTypes.LBRACKET))
        assertTrue(FishTokenTypes.BRACES.contains(FishTypes.RBRACKET))
    }

    @Test
    fun `KEYWORD_TEXTS contains expected keywords`() {
        assertTrue(FishTokenTypes.KEYWORD_TEXTS.contains("function"))
        assertTrue(FishTokenTypes.KEYWORD_TEXTS.contains("end"))
        assertTrue(FishTokenTypes.KEYWORD_TEXTS.contains("if"))
        assertTrue(FishTokenTypes.KEYWORD_TEXTS.contains("else"))
        assertTrue(FishTokenTypes.KEYWORD_TEXTS.contains("for"))
        assertTrue(FishTokenTypes.KEYWORD_TEXTS.contains("in"))
        assertTrue(FishTokenTypes.KEYWORD_TEXTS.contains("while"))
        assertTrue(FishTokenTypes.KEYWORD_TEXTS.contains("switch"))
        assertTrue(FishTokenTypes.KEYWORD_TEXTS.contains("case"))
        assertTrue(FishTokenTypes.KEYWORD_TEXTS.contains("begin"))
        assertTrue(FishTokenTypes.KEYWORD_TEXTS.contains("break"))
        assertTrue(FishTokenTypes.KEYWORD_TEXTS.contains("continue"))
        assertTrue(FishTokenTypes.KEYWORD_TEXTS.contains("return"))
        assertTrue(FishTokenTypes.KEYWORD_TEXTS.contains("set"))
        assertTrue(FishTokenTypes.KEYWORD_TEXTS.contains("and"))
        assertTrue(FishTokenTypes.KEYWORD_TEXTS.contains("or"))
        assertTrue(FishTokenTypes.KEYWORD_TEXTS.contains("not"))
    }

    @Test
    fun `KEYWORD_TEXTS does not contain non-keywords`() {
        assertFalse(FishTokenTypes.KEYWORD_TEXTS.contains("echo"))
        assertFalse(FishTokenTypes.KEYWORD_TEXTS.contains("cd"))
        assertFalse(FishTokenTypes.KEYWORD_TEXTS.contains("ls"))
    }

    @Test
    fun `KEYWORD_TEXTS has expected size`() {
        assertEquals(25, FishTokenTypes.KEYWORD_TEXTS.size)
    }
}
