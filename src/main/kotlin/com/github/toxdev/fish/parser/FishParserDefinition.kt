package com.github.toxdev.fish.parser

import com.github.toxdev.fish.FishLanguage
import com.github.toxdev.fish.lexer.FishLexerAdapter
import com.github.toxdev.fish.psi.FishFile
import com.github.toxdev.fish.psi.FishTokenTypes
import com.github.toxdev.fish.psi.FishTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class FishParserDefinition : ParserDefinition {
    override fun createLexer(project: Project?): Lexer = FishLexerAdapter()

    override fun createParser(project: Project?): PsiParser = FishParser()

    override fun getFileNodeType(): IFileElementType = FILE

    override fun getCommentTokens(): TokenSet = FishTokenTypes.COMMENTS

    override fun getStringLiteralElements(): TokenSet = FishTokenTypes.STRINGS

    override fun createElement(node: ASTNode): PsiElement = FishTypes.Factory.createElement(node)

    override fun createFile(viewProvider: FileViewProvider): PsiFile = FishFile(viewProvider)

    companion object {
        @JvmField
        val FILE = IFileElementType(FishLanguage.INSTANCE)
    }
}
