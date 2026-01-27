package com.github.toxdev.fish.psi

import com.intellij.lang.ASTNode
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FishNamedElementImplTest {
    private lateinit var project: Project
    private lateinit var psiFileFactory: PsiFileFactory
    private lateinit var mockFile: FishFile
    private lateinit var mockAstNode: ASTNode
    private lateinit var chainedStatementNode: ASTNode
    private lateinit var functionBlockNode: ASTNode
    private lateinit var mockFunctionBlock: FishFunctionBlock
    private lateinit var mockFunctionName: FishFunctionName
    private lateinit var mockIdentifier: PsiElement

    @BeforeEach
    fun setUp() {
        project = mockk(relaxed = true)
        psiFileFactory = mockk(relaxed = true)
        mockFile = mockk(relaxed = true)
        mockAstNode = mockk(relaxed = true)
        chainedStatementNode = mockk(relaxed = true)
        functionBlockNode = mockk(relaxed = true)
        mockFunctionBlock = mockk(relaxed = true)
        mockFunctionName = mockk(relaxed = true)
        mockIdentifier = mockk(relaxed = true)

        mockkStatic(PsiFileFactory::class)
        every { PsiFileFactory.getInstance(project) } returns psiFileFactory
    }

    @AfterEach
    fun tearDown() {
        unmockkStatic(PsiFileFactory::class)
    }

    @Test
    fun `FishFunctionName getName returns nameIdentifier text`() {
        every { mockFunctionName.nameIdentifier } returns mockIdentifier
        every { mockIdentifier.text } returns "my_func"
        every { mockFunctionName.name } returns "my_func"

        assertEquals("my_func", mockFunctionName.name)
    }

    @Test
    fun `FishFunctionName getName returns null when nameIdentifier is null`() {
        every { mockFunctionName.nameIdentifier } returns null
        every { mockFunctionName.name } returns null

        assertNull(mockFunctionName.name)
    }

    @Test
    fun `FishFunctionName setName returns element`() {
        every { mockFunctionName.setName("new_name") } returns mockFunctionName

        val result = mockFunctionName.setName("new_name")

        assertEquals(mockFunctionName, result)
    }

    @Test
    fun `FishFunctionName getWord returns word element`() {
        val mockWord = mockk<PsiElement>()
        every { mockFunctionName.word } returns mockWord

        assertEquals(mockWord, mockFunctionName.word)
    }

    @Test
    fun `FishFunctionName getWord returns null when no word`() {
        every { mockFunctionName.word } returns null

        assertNull(mockFunctionName.word)
    }

    @Test
    fun `FishFunctionName getVariable returns variable element`() {
        val mockVariable = mockk<PsiElement>()
        every { mockFunctionName.variable } returns mockVariable

        assertEquals(mockVariable, mockFunctionName.variable)
    }

    @Test
    fun `FishFunctionName getVariable returns null when no variable`() {
        every { mockFunctionName.variable } returns null

        assertNull(mockFunctionName.variable)
    }

    @Test
    fun `FishNamedElement interface extends PsiNameIdentifierOwner`() {
        val superInterfaces = FishNamedElement::class.java.interfaces.map { it.name }
        assert(superInterfaces.contains("com.intellij.psi.PsiNameIdentifierOwner"))
    }
}
