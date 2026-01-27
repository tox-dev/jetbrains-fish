package com.github.toxdev.fish.psi

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class FishPsiImplUtilTest {
    @Test
    fun `getName returns null when functionName is null`() {
        val mockFunctionBlock = mockk<FishFunctionBlock>(relaxed = true)
        every { mockFunctionBlock.functionName } returns null

        assertNull(FishPsiImplUtil.getName(mockFunctionBlock))
    }

    @Test
    fun `getName returns null when functionName name is null`() {
        val mockFunctionName = mockk<FishFunctionName>(relaxed = true)
        every { mockFunctionName.name } returns null
        val mockFunctionBlock = mockk<FishFunctionBlock>(relaxed = true)
        every { mockFunctionBlock.functionName } returns mockFunctionName

        assertNull(FishPsiImplUtil.getName(mockFunctionBlock))
    }

    @Test
    fun `getName returns name from functionName`() {
        val mockFunctionName = mockk<FishFunctionName>(relaxed = true)
        every { mockFunctionName.name } returns "my_func"
        val mockFunctionBlock = mockk<FishFunctionBlock>(relaxed = true)
        every { mockFunctionBlock.functionName } returns mockFunctionName

        assertEquals("my_func", FishPsiImplUtil.getName(mockFunctionBlock))
    }

    @Test
    fun `setName returns element unchanged when functionName is null`() {
        val mockFunctionBlock = mockk<FishFunctionBlock>(relaxed = true)
        every { mockFunctionBlock.functionName } returns null

        val result = FishPsiImplUtil.setName(mockFunctionBlock, "new_name")

        assertEquals(mockFunctionBlock, result)
    }

    @Test
    fun `setName calls setName on functionName`() {
        val mockFunctionName = mockk<FishFunctionName>(relaxed = true)
        val mockFunctionBlock = mockk<FishFunctionBlock>(relaxed = true)
        every { mockFunctionBlock.functionName } returns mockFunctionName

        FishPsiImplUtil.setName(mockFunctionBlock, "new_name")

        verify { mockFunctionName.setName("new_name") }
    }

    @Test
    fun `getNameIdentifier returns null when functionName is null`() {
        val mockFunctionBlock = mockk<FishFunctionBlock>(relaxed = true)
        every { mockFunctionBlock.functionName } returns null

        assertNull(FishPsiImplUtil.getNameIdentifier(mockFunctionBlock))
    }

    @Test
    fun `getNameIdentifier returns null when nameIdentifier is null`() {
        val mockFunctionName = mockk<FishFunctionName>(relaxed = true)
        every { mockFunctionName.nameIdentifier } returns null
        val mockFunctionBlock = mockk<FishFunctionBlock>(relaxed = true)
        every { mockFunctionBlock.functionName } returns mockFunctionName

        assertNull(FishPsiImplUtil.getNameIdentifier(mockFunctionBlock))
    }

    @Test
    fun `getNameIdentifier returns identifier from functionName`() {
        val mockIdentifier = mockk<com.intellij.psi.PsiElement>()
        val mockFunctionName = mockk<FishFunctionName>(relaxed = true)
        every { mockFunctionName.nameIdentifier } returns mockIdentifier
        val mockFunctionBlock = mockk<FishFunctionBlock>(relaxed = true)
        every { mockFunctionBlock.functionName } returns mockFunctionName

        assertEquals(mockIdentifier, FishPsiImplUtil.getNameIdentifier(mockFunctionBlock))
    }
}
