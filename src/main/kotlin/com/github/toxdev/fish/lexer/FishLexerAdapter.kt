package com.github.toxdev.fish.lexer

import com.intellij.lexer.FlexAdapter

/**
 * Adapter to use the generated JFlex lexer with IntelliJ Platform.
 */
class FishLexerAdapter : FlexAdapter(_FishLexer(null))
