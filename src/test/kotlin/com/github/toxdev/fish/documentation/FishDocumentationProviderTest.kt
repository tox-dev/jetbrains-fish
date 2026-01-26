package com.github.toxdev.fish.documentation

import com.github.toxdev.fish.psi.FishTokenTypes
import com.github.toxdev.fish.psi.FishTypes
import com.intellij.psi.PsiElement
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FishDocumentationProviderTest {
    private val provider = FishDocumentationProvider()

    @Test
    fun `generateDoc returns null for null element`() {
        assertNull(provider.generateDoc(null, null))
    }

    @Test
    fun `generateDoc returns documentation for variable`() {
        val element = mockElement(FishTypes.VARIABLE, "\$status")
        val doc = provider.generateDoc(element, null)
        assertNotNull(doc)
        assertTrue(doc!!.contains("exit status"))
    }

    @Test
    fun `generateDoc returns documentation for pipe operator`() {
        val element = mockElement(FishTypes.PIPE, "|")
        val doc = provider.generateDoc(element, null)
        assertNotNull(doc)
        assertTrue(doc!!.contains("pipe"))
    }

    @Test
    fun `generateDoc returns documentation for and operator`() {
        val element = mockElement(FishTypes.AND_AND, "&&")
        val doc = provider.generateDoc(element, null)
        assertNotNull(doc)
        assertTrue(doc!!.contains("and"))
    }

    @Test
    fun `generateDoc returns documentation for or operator`() {
        val element = mockElement(FishTypes.OR_OR, "||")
        val doc = provider.generateDoc(element, null)
        assertNotNull(doc)
        assertTrue(doc!!.contains("or"))
    }

    @Test
    fun `generateDoc returns documentation for background operator`() {
        val element = mockElement(FishTypes.BACKGROUND, "&")
        val doc = provider.generateDoc(element, null)
        assertNotNull(doc)
        assertTrue(doc!!.contains("background"))
    }

    @Test
    fun `generateDoc returns documentation for keyword`() {
        val element = mockElement(FishTokenTypes.KEYWORD, "if")
        val doc = provider.generateDoc(element, null)
        assertNotNull(doc)
        assertTrue(doc!!.contains("conditionally"))
    }

    @Test
    fun `generateDoc returns documentation for redirect output`() {
        val element = mockElement(FishTypes.REDIRECT, ">")
        val doc = provider.generateDoc(element, null)
        assertNotNull(doc)
        assertTrue(doc!!.contains("redirect"))
    }

    @Test
    fun `generateDoc returns documentation for redirect append`() {
        val element = mockElement(FishTypes.REDIRECT, ">>")
        val doc = provider.generateDoc(element, null)
        assertNotNull(doc)
        assertTrue(doc!!.contains("append"))
    }

    @Test
    fun `generateDoc returns documentation for redirect input`() {
        val element = mockElement(FishTypes.REDIRECT, "<")
        val doc = provider.generateDoc(element, null)
        assertNotNull(doc)
        assertTrue(doc!!.contains("input"))
    }

    @Test
    fun `generateDoc returns documentation for stderr redirect`() {
        val element = mockElement(FishTypes.REDIRECT, "2>")
        val doc = provider.generateDoc(element, null)
        assertNotNull(doc)
        assertTrue(doc!!.contains("stderr"))
    }

    @Test
    fun `generateDoc returns documentation for combined redirect`() {
        val element = mockElement(FishTypes.REDIRECT, "&>")
        val doc = provider.generateDoc(element, null)
        assertNotNull(doc)
        assertTrue(doc!!.contains("all output"))
    }

    @Test
    fun `generateDoc returns null for unknown word`() {
        val element = mockWordElement("some_random_word", hasPrevSibling = false)
        val doc = provider.generateDoc(element, null)
        assertNull(doc)
    }

    @Test
    fun `generateDoc returns documentation for known command as word in command position`() {
        val element = mockWordElement("echo", hasPrevSibling = false)
        val doc = provider.generateDoc(element, null)
        assertNotNull(doc)
        assertTrue(doc!!.contains("display a line of text"))
    }

    @Test
    fun `generateDoc returns documentation for keyword text in word element`() {
        val element = mockWordElement("if", hasPrevSibling = false)
        val doc = provider.generateDoc(element, null)
        assertNotNull(doc)
        assertTrue(doc!!.contains("conditionally"))
    }

    @Test
    fun `getCustomDocumentationElement returns context element`() {
        val editor = mockk<com.intellij.openapi.editor.Editor>()
        val file = mockk<com.intellij.psi.PsiFile>()
        val element = mockk<PsiElement>()
        val result = provider.getCustomDocumentationElement(editor, file, element, 0)
        assert(result === element)
    }

    @Test
    fun `generateDoc returns subcommand documentation for string length`() {
        val element = mockSubcommandElement("length", "string")
        val doc = provider.generateDoc(element, null)
        assertNotNull(doc)
        assertTrue(doc!!.contains("string length"))
        assertTrue(doc.contains("print string lengths"))
    }

    @Test
    fun `generateDoc returns subcommand documentation for status is-interactive`() {
        val element = mockSubcommandElement("is-interactive", "status")
        val doc = provider.generateDoc(element, null)
        assertNotNull(doc)
        assertTrue(doc!!.contains("status is-interactive"))
        assertTrue(doc.contains("interactive"))
    }

    @Test
    fun `generateDoc returns subcommand documentation for path basename`() {
        val element = mockSubcommandElement("basename", "path")
        val doc = provider.generateDoc(element, null)
        assertNotNull(doc)
        assertTrue(doc!!.contains("path basename"))
        assertTrue(doc.contains("last path component"))
    }

    @Test
    fun `generateDoc returns subcommand documentation for history search`() {
        val element = mockSubcommandElement("search", "history")
        val doc = provider.generateDoc(element, null)
        assertNotNull(doc)
        assertTrue(doc!!.contains("history search"))
        assertTrue(doc.contains("search command history"))
    }

    @Test
    fun `generateDoc returns null for unknown subcommand`() {
        val element = mockSubcommandElement("unknown", "string")
        val doc = provider.generateDoc(element, null)
        assertNull(doc)
    }

    @Test
    fun `generateDoc returns null for subcommand of non-subcommand parent`() {
        val element = mockSubcommandElement("length", "echo")
        val doc = provider.generateDoc(element, null)
        assertNull(doc)
    }

    private fun mockElement(
        elementType: com.intellij.psi.tree.IElementType,
        text: String,
    ): PsiElement {
        val element = mockk<PsiElement>()
        every { element.node } returns
            mockk {
                every { this@mockk.elementType } returns elementType
            }
        every { element.text } returns text
        return element
    }

    private fun mockWordElement(
        text: String,
        hasPrevSibling: Boolean,
    ): PsiElement {
        val element = mockk<PsiElement>()
        every { element.node } returns
            mockk {
                every { this@mockk.elementType } returns FishTypes.WORD
            }
        every { element.text } returns text
        every { element.prevSibling } returns if (hasPrevSibling) mockk() else null
        every { element.parent } returns null
        return element
    }

    private fun mockSubcommandElement(
        subcommand: String,
        parentCommand: String,
    ): PsiElement {
        val whitespace = mockk<PsiElement>()
        every { whitespace.node } returns
            mockk {
                every { this@mockk.elementType } returns FishTokenTypes.WHITE_SPACE
            }

        val parentElement = mockk<PsiElement>()
        every { parentElement.node } returns
            mockk {
                every { this@mockk.elementType } returns FishTypes.WORD
            }
        every { parentElement.text } returns parentCommand
        every { whitespace.prevSibling } returns parentElement

        val element = mockk<PsiElement>()
        every { element.node } returns
            mockk {
                every { this@mockk.elementType } returns FishTypes.WORD
            }
        every { element.text } returns subcommand
        every { element.prevSibling } returns whitespace
        every { element.parent } returns null
        return element
    }
}
