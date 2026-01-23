package com.github.toxdev.fish

import com.intellij.lang.Commenter

/**
 * Commenter for Fish shell language.
 * Enables line comment toggle with Ctrl+/.
 */
class FishCommenter : Commenter {
    override fun getLineCommentPrefix(): String = "# "

    override fun getBlockCommentPrefix(): String? = null

    override fun getBlockCommentSuffix(): String? = null

    override fun getCommentedBlockCommentPrefix(): String? = null

    override fun getCommentedBlockCommentSuffix(): String? = null
}
