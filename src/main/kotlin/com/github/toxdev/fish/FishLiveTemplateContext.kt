package com.github.toxdev.fish

import com.intellij.codeInsight.template.TemplateActionContext
import com.intellij.codeInsight.template.TemplateContextType

@Suppress("deprecation")
class FishLiveTemplateContext : TemplateContextType("Fish") {
    override fun isInContext(context: TemplateActionContext): Boolean = context.file.fileType == FishFileType.INSTANCE
}
