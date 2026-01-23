package com.github.toxdev.fish.psi

import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

@Suppress("MemberVisibilityCanBePrivate")
object FishTokenTypes {
    @JvmField val WHITE_SPACE: IElementType = TokenType.WHITE_SPACE

    @JvmField val BAD_CHARACTER: IElementType = TokenType.BAD_CHARACTER

    @JvmField val KEYWORD: IElementType = FishTokenType("KEYWORD")

    @JvmField val COMMENTS: TokenSet = TokenSet.create(FishTypes.COMMENT, FishTypes.SHEBANG)

    @JvmField val STRINGS: TokenSet = TokenSet.create(FishTypes.SINGLE_QUOTE, FishTypes.DOUBLE_QUOTE, FishTypes.STRING_CONTENT)

    @JvmField val KEYWORDS: TokenSet = TokenSet.create(KEYWORD)

    @JvmField val BRACES: TokenSet =
        TokenSet.create(
            FishTypes.LPAREN,
            FishTypes.RPAREN,
            FishTypes.LBRACE,
            FishTypes.RBRACE,
            FishTypes.LBRACKET,
            FishTypes.RBRACKET,
        )

    val KEYWORD_TEXTS: Set<String> =
        setOf(
            "function",
            "end",
            "if",
            "else",
            "for",
            "in",
            "while",
            "switch",
            "case",
            "begin",
            "break",
            "continue",
            "return",
            "set",
            "and",
            "or",
            "not",
            "builtin",
            "command",
            "exec",
            "time",
            "read",
            "test",
            "string",
            "status",
        )
}
