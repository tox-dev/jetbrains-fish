package com.github.toxdev.fish.psi

import com.github.toxdev.fish.FishFileType
import com.intellij.lang.ASTNode
import com.intellij.lang.FileASTNode
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FishElementFactoryTest {
    private lateinit var project: Project
    private lateinit var psiFileFactory: PsiFileFactory
    private lateinit var mockFile: FishFile
    private lateinit var mockAstNode: FileASTNode
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
        mockAstNode = mockk<FileASTNode>(relaxed = true)
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
    fun `createFunctionName returns identifier for valid function`() {
        every {
            psiFileFactory.createFileFromText("dummy.fish", FishFileType.INSTANCE, "function test_func\nend")
        } returns mockFile
        every { mockFile.node } returns mockAstNode
        every { mockAstNode.findChildByType(FishTypes.CHAINED_STATEMENT) } returns chainedStatementNode
        every { chainedStatementNode.findChildByType(FishTypes.FUNCTION_BLOCK) } returns functionBlockNode
        every { functionBlockNode.psi } returns mockFunctionBlock
        every { mockFunctionBlock.functionName } returns mockFunctionName
        every { mockFunctionName.nameIdentifier } returns mockIdentifier

        val result = FishElementFactory.createFunctionName(project, "test_func")

        assertEquals(mockIdentifier, result)
    }

    @Test
    fun `createFunctionName throws when chained statement not found`() {
        every {
            psiFileFactory.createFileFromText("dummy.fish", FishFileType.INSTANCE, "function test_func\nend")
        } returns mockFile
        every { mockFile.node } returns mockAstNode
        every { mockAstNode.findChildByType(FishTypes.CHAINED_STATEMENT) } returns null

        assertThrows(IllegalStateException::class.java) {
            FishElementFactory.createFunctionName(project, "test_func")
        }
    }

    @Test
    fun `createFunctionName throws when function block not found`() {
        every {
            psiFileFactory.createFileFromText("dummy.fish", FishFileType.INSTANCE, "function test_func\nend")
        } returns mockFile
        every { mockFile.node } returns mockAstNode
        every { mockAstNode.findChildByType(FishTypes.CHAINED_STATEMENT) } returns chainedStatementNode
        every { chainedStatementNode.findChildByType(FishTypes.FUNCTION_BLOCK) } returns null

        assertThrows(IllegalStateException::class.java) {
            FishElementFactory.createFunctionName(project, "test_func")
        }
    }

    @Test
    fun `createFunctionName throws when psi is not FishFunctionBlock`() {
        every {
            psiFileFactory.createFileFromText("dummy.fish", FishFileType.INSTANCE, "function test_func\nend")
        } returns mockFile
        every { mockFile.node } returns mockAstNode
        every { mockAstNode.findChildByType(FishTypes.CHAINED_STATEMENT) } returns chainedStatementNode
        every { chainedStatementNode.findChildByType(FishTypes.FUNCTION_BLOCK) } returns functionBlockNode
        every { functionBlockNode.psi } returns mockk<PsiElement>()

        assertThrows(IllegalStateException::class.java) {
            FishElementFactory.createFunctionName(project, "test_func")
        }
    }

    @Test
    fun `createFunctionName throws when functionName is null`() {
        every {
            psiFileFactory.createFileFromText("dummy.fish", FishFileType.INSTANCE, "function test_func\nend")
        } returns mockFile
        every { mockFile.node } returns mockAstNode
        every { mockAstNode.findChildByType(FishTypes.CHAINED_STATEMENT) } returns chainedStatementNode
        every { chainedStatementNode.findChildByType(FishTypes.FUNCTION_BLOCK) } returns functionBlockNode
        every { functionBlockNode.psi } returns mockFunctionBlock
        every { mockFunctionBlock.functionName } returns null

        assertThrows(IllegalStateException::class.java) {
            FishElementFactory.createFunctionName(project, "test_func")
        }
    }

    @Test
    fun `createFunctionName throws when nameIdentifier is null`() {
        every {
            psiFileFactory.createFileFromText("dummy.fish", FishFileType.INSTANCE, "function test_func\nend")
        } returns mockFile
        every { mockFile.node } returns mockAstNode
        every { mockAstNode.findChildByType(FishTypes.CHAINED_STATEMENT) } returns chainedStatementNode
        every { chainedStatementNode.findChildByType(FishTypes.FUNCTION_BLOCK) } returns functionBlockNode
        every { functionBlockNode.psi } returns mockFunctionBlock
        every { mockFunctionBlock.functionName } returns mockFunctionName
        every { mockFunctionName.nameIdentifier } returns null

        assertThrows(IllegalStateException::class.java) {
            FishElementFactory.createFunctionName(project, "test_func")
        }
    }
}
