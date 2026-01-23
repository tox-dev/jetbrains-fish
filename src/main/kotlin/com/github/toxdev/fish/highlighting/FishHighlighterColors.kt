package com.github.toxdev.fish.highlighting

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey

/**
 * Text attribute keys for Fish shell syntax highlighting.
 */
object FishHighlighterColors {
    // Keywords
    @JvmField
    val KEYWORD: TextAttributesKey =
        createTextAttributesKey(
            "FISH_KEYWORD",
            DefaultLanguageHighlighterColors.KEYWORD,
        )

    @JvmField
    val KEYWORD_SUB: TextAttributesKey =
        createTextAttributesKey(
            "FISH_KEYWORD_SUB",
            DefaultLanguageHighlighterColors.KEYWORD,
        )

    // Comments
    @JvmField
    val COMMENT: TextAttributesKey =
        createTextAttributesKey(
            "FISH_COMMENT",
            DefaultLanguageHighlighterColors.LINE_COMMENT,
        )

    @JvmField
    val SHEBANG: TextAttributesKey =
        createTextAttributesKey(
            "FISH_SHEBANG",
            DefaultLanguageHighlighterColors.LINE_COMMENT,
        )

    // Strings
    @JvmField
    val STRING: TextAttributesKey =
        createTextAttributesKey(
            "FISH_STRING",
            DefaultLanguageHighlighterColors.STRING,
        )

    @JvmField
    val ESCAPE: TextAttributesKey =
        createTextAttributesKey(
            "FISH_ESCAPE",
            DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE,
        )

    // Variables
    @JvmField
    val VARIABLE: TextAttributesKey =
        createTextAttributesKey(
            "FISH_VARIABLE",
            DefaultLanguageHighlighterColors.GLOBAL_VARIABLE,
        )

    // Numbers
    @JvmField
    val NUMBER: TextAttributesKey =
        createTextAttributesKey(
            "FISH_NUMBER",
            DefaultLanguageHighlighterColors.NUMBER,
        )

    // Operators
    @JvmField
    val OPERATOR: TextAttributesKey =
        createTextAttributesKey(
            "FISH_OPERATOR",
            DefaultLanguageHighlighterColors.OPERATION_SIGN,
        )

    @JvmField
    val REDIRECT: TextAttributesKey =
        createTextAttributesKey(
            "FISH_REDIRECT",
            DefaultLanguageHighlighterColors.OPERATION_SIGN,
        )

    // Brackets
    @JvmField
    val PARENTHESES: TextAttributesKey =
        createTextAttributesKey(
            "FISH_PARENTHESES",
            DefaultLanguageHighlighterColors.PARENTHESES,
        )

    @JvmField
    val BRACES: TextAttributesKey =
        createTextAttributesKey(
            "FISH_BRACES",
            DefaultLanguageHighlighterColors.BRACES,
        )

    @JvmField
    val BRACKETS: TextAttributesKey =
        createTextAttributesKey(
            "FISH_BRACKETS",
            DefaultLanguageHighlighterColors.BRACKETS,
        )

    // Commands/Words
    @JvmField
    val COMMAND: TextAttributesKey =
        createTextAttributesKey(
            "FISH_COMMAND",
            DefaultLanguageHighlighterColors.FUNCTION_CALL,
        )

    // Bad character
    @JvmField
    val BAD_CHARACTER: TextAttributesKey =
        createTextAttributesKey(
            "FISH_BAD_CHARACTER",
            HighlighterColors.BAD_CHARACTER,
        )
}
