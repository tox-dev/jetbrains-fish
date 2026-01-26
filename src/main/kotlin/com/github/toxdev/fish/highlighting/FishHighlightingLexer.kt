package com.github.toxdev.fish.highlighting

import com.github.toxdev.fish.lexer.FishLexerAdapter
import com.github.toxdev.fish.psi.FishTokenTypes
import com.github.toxdev.fish.psi.FishTypes
import com.intellij.lexer.LexerBase
import com.intellij.psi.tree.IElementType

class FishHighlightingLexer : LexerBase() {
    private val delegate = FishLexerAdapter()

    override fun start(
        buffer: CharSequence,
        startOffset: Int,
        endOffset: Int,
        initialState: Int,
    ) {
        delegate.start(buffer, startOffset, endOffset, initialState)
    }

    override fun getState(): Int = delegate.state

    override fun getTokenType(): IElementType? {
        val type = delegate.tokenType ?: return null
        if (type == FishTypes.WORD && delegate.tokenText in FishTokenTypes.KEYWORD_TEXTS) {
            return FishTokenTypes.KEYWORD
        }
        return type
    }

    override fun getTokenStart(): Int = delegate.tokenStart

    override fun getTokenEnd(): Int = delegate.tokenEnd

    override fun advance() {
        delegate.advance()
    }

    override fun getBufferSequence(): CharSequence = delegate.bufferSequence

    override fun getBufferEnd(): Int = delegate.bufferEnd
}
