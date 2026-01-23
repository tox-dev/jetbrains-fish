package com.github.toxdev.fish.parser

import com.github.toxdev.fish.psi.FishFile
import com.github.toxdev.fish.psi.FishFunctionBlock
import com.intellij.openapi.application.ApplicationManager
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.junit5.TestApplication
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.util.stream.Stream

@TestApplication
class FishParsingTest {
    companion object {
        private val testDataRoot = File("src/test/testData")

        private val EXCLUDED_FILES =
            setOf(
                "line-continuation.fish",
            )

        @JvmStatic
        fun allFishFiles(): Stream<File> =
            testDataRoot
                .walk()
                .filter { it.extension == "fish" }
                .filter { it.name !in EXCLUDED_FILES }
                .toList()
                .stream()
    }

    @Test
    fun `test simple function declaration`() {
        val content =
            """
            function my_func
                echo "hello"
            end
            """.trimIndent()

        val (file, errors, functions) =
            ApplicationManager
                .getApplication()
                .runReadAction<Triple<FishFile, Collection<PsiErrorElement>, Collection<FishFunctionBlock>>> {
                    val f = createPsiFile("test.fish", content)
                    val e = PsiTreeUtil.findChildrenOfType(f, PsiErrorElement::class.java)
                    val fn = PsiTreeUtil.findChildrenOfType(f, FishFunctionBlock::class.java)
                    Triple(f, e, fn)
                }

        assertTrue(errors.isEmpty(), "Should parse without errors, but found: ${formatErrors(errors)}")
        assertEquals(1, functions.size, "Expected 1 function")
    }

    @Test
    fun `test function with underscore in name`() {
        val content =
            """
            function fish_greeting
            end
            """.trimIndent()

        val (errors, functions) =
            ApplicationManager.getApplication().runReadAction<Pair<Collection<PsiErrorElement>, Collection<FishFunctionBlock>>> {
                val f = createPsiFile("test.fish", content)
                val e = PsiTreeUtil.findChildrenOfType(f, PsiErrorElement::class.java)
                val fn = PsiTreeUtil.findChildrenOfType(f, FishFunctionBlock::class.java)
                Pair(e, fn)
            }

        assertTrue(errors.isEmpty(), "Should parse without errors, but found: ${formatErrors(errors)}")
        assertEquals(1, functions.size, "Expected 1 function")
        assertEquals(
            "fish_greeting",
            functions.first().functionName?.text,
            "Expected function name 'fish_greeting'",
        )
    }

    @Test
    fun `test fish_greeting file parses without errors`() {
        val fishGreetingFile = File("src/test/testData/prompts/fish_greeting.fish")
        val content = fishGreetingFile.readText()

        val (errors, functions) =
            ApplicationManager.getApplication().runReadAction<Pair<Collection<PsiErrorElement>, Collection<FishFunctionBlock>>> {
                val f = createPsiFile("fish_greeting.fish", content)
                val e = PsiTreeUtil.findChildrenOfType(f, PsiErrorElement::class.java)
                val fn = PsiTreeUtil.findChildrenOfType(f, FishFunctionBlock::class.java)
                Pair(e, fn)
            }

        assertTrue(
            errors.isEmpty(),
            "fish_greeting.fish should parse without errors, but found: ${formatErrors(errors)}",
        )
        assertEquals(1, functions.size, "Expected 1 function in fish_greeting.fish")
        assertEquals(
            "fish_greeting",
            functions.first().functionName?.text,
            "Expected function name 'fish_greeting'",
        )
    }

    @Test
    fun `test variable as function name`() {
        val content =
            """
            function ${'$'}color --on-variable color
                echo "color changed"
            end
            """.trimIndent()

        val errors =
            ApplicationManager.getApplication().runReadAction<Collection<PsiErrorElement>> {
                val f = createPsiFile("test.fish", content)
                PsiTreeUtil.findChildrenOfType(f, PsiErrorElement::class.java)
            }

        assertTrue(errors.isEmpty(), "Should parse variable as function name without errors: ${formatErrors(errors)}")
    }

    @Test
    fun `test brace expansion in for loop`() {
        val content =
            """
            for color in bgp_color_{pwd,git,error}
                echo ${'$'}color
            end
            """.trimIndent()

        val errors =
            ApplicationManager.getApplication().runReadAction<Collection<PsiErrorElement>> {
                val f = createPsiFile("test.fish", content)
                PsiTreeUtil.findChildrenOfType(f, PsiErrorElement::class.java)
            }

        assertTrue(errors.isEmpty(), "Should parse brace expansion without errors: ${formatErrors(errors)}")
    }

    @Test
    fun `test statement chaining after function block`() {
        val content =
            """
            function test_func
                echo "hello"
            end && echo "done"
            """.trimIndent()

        val errors =
            ApplicationManager.getApplication().runReadAction<Collection<PsiErrorElement>> {
                val f = createPsiFile("test.fish", content)
                PsiTreeUtil.findChildrenOfType(f, PsiErrorElement::class.java)
            }

        assertTrue(errors.isEmpty(), "Should parse chained statement after function block: ${formatErrors(errors)}")
    }

    @Test
    fun `test complex dynamic function pattern`() {
        val content =
            """
            for color in bgp_color_{pwd,git,error,prompt,duration,context}
                function ${'$'}color --on-variable ${'$'}color --inherit-variable color
                    set --query ${'$'}color && set --global _${'$'}color (set_color ${'$'}color)
                end && ${'$'}color
            end
            """.trimIndent()

        val errors =
            ApplicationManager.getApplication().runReadAction<Collection<PsiErrorElement>> {
                val f = createPsiFile("test.fish", content)
                PsiTreeUtil.findChildrenOfType(f, PsiErrorElement::class.java)
            }

        assertTrue(errors.isEmpty(), "Should parse complex dynamic function pattern: ${formatErrors(errors)}")
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("allFishFiles")
    fun `parse all test data files without errors`(file: File) {
        val relativePath = file.relativeTo(testDataRoot).path
        val content = file.readText()

        val errors =
            ApplicationManager.getApplication().runReadAction<Collection<PsiErrorElement>> {
                val psiFile = createPsiFile(file.name, content)
                PsiTreeUtil.findChildrenOfType(psiFile, PsiErrorElement::class.java)
            }

        assertTrue(
            errors.isEmpty(),
            "Parse errors in $relativePath:\n${formatErrors(errors)}",
        )
    }

    private fun createPsiFile(
        fileName: String,
        content: String,
    ): FishFile {
        val factory = PsiFileFactory.getInstance(projectInstance)
        return factory.createFileFromText(
            fileName,
            com.github.toxdev.fish.FishFileType.INSTANCE,
            content,
        ) as FishFile
    }

    private fun formatErrors(errors: Collection<PsiErrorElement>): String =
        if (errors.isEmpty()) {
            ""
        } else {
            errors.joinToString("\n") { error ->
                "  ${error.errorDescription} at '${error.text}'"
            }
        }

    private val projectInstance: com.intellij.openapi.project.Project
        get() {
            val projectManager =
                com.intellij.openapi.project.ProjectManager
                    .getInstance()
            return projectManager.defaultProject
        }
}
