package com.github.toxdev.fish.highlighting

import com.github.toxdev.fish.psi.FishTokenTypes
import com.github.toxdev.fish.psi.FishTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType

class FishSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer = FishHighlightingLexer()

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> = pack(TOKEN_MAP[tokenType])

    companion object {
        private val TOKEN_MAP: Map<IElementType, TextAttributesKey> =
            mapOf(
                FishTokenTypes.KEYWORD to FishHighlighterColors.KEYWORD,
                FishTypes.COMMENT to FishHighlighterColors.COMMENT,
                FishTypes.SHEBANG to FishHighlighterColors.SHEBANG,
                FishTypes.SINGLE_QUOTE to FishHighlighterColors.STRING,
                FishTypes.DOUBLE_QUOTE to FishHighlighterColors.STRING,
                FishTypes.STRING_CONTENT to FishHighlighterColors.STRING,
                FishTypes.ESCAPE to FishHighlighterColors.ESCAPE,
                FishTypes.VARIABLE to FishHighlighterColors.VARIABLE,
                FishTypes.NUMBER to FishHighlighterColors.NUMBER,
                FishTypes.PIPE to FishHighlighterColors.OPERATOR,
                FishTypes.AND_AND to FishHighlighterColors.OPERATOR,
                FishTypes.OR_OR to FishHighlighterColors.OPERATOR,
                FishTypes.BACKGROUND to FishHighlighterColors.OPERATOR,
                FishTypes.REDIRECT to FishHighlighterColors.REDIRECT,
                FishTypes.SEMICOLON to FishHighlighterColors.OPERATOR,
                FishTypes.LPAREN to FishHighlighterColors.PARENTHESES,
                FishTypes.RPAREN to FishHighlighterColors.PARENTHESES,
                FishTypes.LBRACE to FishHighlighterColors.BRACES,
                FishTypes.RBRACE to FishHighlighterColors.BRACES,
                FishTypes.LBRACKET to FishHighlighterColors.BRACKETS,
                FishTypes.RBRACKET to FishHighlighterColors.BRACKETS,
                FishTypes.WORD to FishHighlighterColors.COMMAND,
                FishTokenTypes.BAD_CHARACTER to FishHighlighterColors.BAD_CHARACTER,
            )
    }
}
