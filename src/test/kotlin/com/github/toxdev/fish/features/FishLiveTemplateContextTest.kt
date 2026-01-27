package com.github.toxdev.fish.features

import com.github.toxdev.fish.FishFileType
import com.github.toxdev.fish.FishLiveTemplateContext
import com.intellij.codeInsight.template.TemplateActionContext
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.psi.PsiFile
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FishLiveTemplateContextTest {
    private val context = FishLiveTemplateContext()

    @Test
    fun `isInContext returns true for Fish files`() {
        val templateContext = createTemplateContext(FishFileType.INSTANCE)
        assertTrue(context.isInContext(templateContext))
    }

    @Test
    fun `isInContext returns false for non-Fish files`() {
        val templateContext = createTemplateContext(PlainTextFileType.INSTANCE)
        assertFalse(context.isInContext(templateContext))
    }

    private fun createTemplateContext(fileType: FileType): TemplateActionContext {
        val psiFile =
            mockk<PsiFile> {
                every { this@mockk.fileType } returns fileType
            }
        return mockk {
            every { file } returns psiFile
        }
    }
}
