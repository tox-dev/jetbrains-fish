package com.github.toxdev.fish.structure

import com.github.toxdev.fish.psi.FishFile
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class FishStructureViewFactoryTest {
    private val factory = FishStructureViewFactory()

    @Test
    fun `getStructureViewBuilder returns null for non-FishFile`() {
        val psiFile = mockk<com.intellij.psi.PsiFile>(relaxed = true)
        val result = factory.getStructureViewBuilder(psiFile)
        assertNull(result)
    }

    @Test
    fun `getStructureViewBuilder returns builder for FishFile`() {
        val fishFile = mockk<FishFile>(relaxed = true)
        val result = factory.getStructureViewBuilder(fishFile)
        assertNotNull(result)
    }

    @Test
    fun `builder createStructureViewModel returns FishStructureViewModel`() {
        val fishFile = mockk<FishFile>(relaxed = true)
        val builder = factory.getStructureViewBuilder(fishFile) as TreeBasedStructureViewBuilder
        val viewModel = builder.createStructureViewModel(null)
        assertNotNull(viewModel)
    }
}
