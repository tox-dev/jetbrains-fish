package com.github.toxdev.fish.structure

import com.github.toxdev.fish.psi.FishFile
import com.github.toxdev.fish.psi.FishFunctionBlock
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.Sorter
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FishStructureViewModelTest {
    @Test
    fun `getSorters returns alpha sorter`() {
        val fishFile = mockk<FishFile>(relaxed = true)
        val model = FishStructureViewModel(fishFile, null)
        val sorters = model.sorters
        assertEquals(1, sorters.size)
        assertEquals(Sorter.ALPHA_SORTER, sorters[0])
    }

    @Test
    fun `isAlwaysShowsPlus returns false`() {
        val fishFile = mockk<FishFile>(relaxed = true)
        val model = FishStructureViewModel(fishFile, null)
        val element = mockk<StructureViewTreeElement>(relaxed = true)
        assertFalse(model.isAlwaysShowsPlus(element))
    }

    @Test
    fun `isAlwaysLeaf returns true for FishFunctionBlock`() {
        val fishFile = mockk<FishFile>(relaxed = true)
        val model = FishStructureViewModel(fishFile, null)
        val functionBlock = mockk<FishFunctionBlock>(relaxed = true)
        val element = mockk<StructureViewTreeElement>(relaxed = true)
        every { element.value } returns functionBlock
        assertTrue(model.isAlwaysLeaf(element))
    }

    @Test
    fun `isAlwaysLeaf returns false for non-FishFunctionBlock`() {
        val fishFile = mockk<FishFile>(relaxed = true)
        val model = FishStructureViewModel(fishFile, null)
        val element = mockk<StructureViewTreeElement>(relaxed = true)
        every { element.value } returns fishFile
        assertFalse(model.isAlwaysLeaf(element))
    }
}
