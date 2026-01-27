package com.github.toxdev.fish

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class FishCommenterTest {
    private val commenter = FishCommenter()

    @Test
    fun `getLineCommentPrefix returns hash with space`() {
        assertEquals("# ", commenter.lineCommentPrefix)
    }

    @Test
    fun `getBlockCommentPrefix returns null`() {
        assertNull(commenter.blockCommentPrefix)
    }

    @Test
    fun `getBlockCommentSuffix returns null`() {
        assertNull(commenter.blockCommentSuffix)
    }

    @Test
    fun `getCommentedBlockCommentPrefix returns null`() {
        assertNull(commenter.commentedBlockCommentPrefix)
    }

    @Test
    fun `getCommentedBlockCommentSuffix returns null`() {
        assertNull(commenter.commentedBlockCommentSuffix)
    }
}
