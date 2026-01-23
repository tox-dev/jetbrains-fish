package com.github.toxdev.fish.highlighting

import com.github.toxdev.fish.FishIcons
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

/**
 * Color settings page for Fish shell syntax highlighting customization.
 */
class FishColorSettingsPage : ColorSettingsPage {
    override fun getIcon(): Icon = FishIcons.FILE

    override fun getHighlighter(): SyntaxHighlighter = FishSyntaxHighlighter()

    override fun getDemoText(): String = DEMO_TEXT

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? = null

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = DESCRIPTORS

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getDisplayName(): String = "Fish"

    companion object {
        private val DESCRIPTORS =
            arrayOf(
                AttributesDescriptor("Keyword", FishHighlighterColors.KEYWORD),
                AttributesDescriptor("Subcommand keyword", FishHighlighterColors.KEYWORD_SUB),
                AttributesDescriptor("Comment", FishHighlighterColors.COMMENT),
                AttributesDescriptor("Shebang", FishHighlighterColors.SHEBANG),
                AttributesDescriptor("String", FishHighlighterColors.STRING),
                AttributesDescriptor("Escape sequence", FishHighlighterColors.ESCAPE),
                AttributesDescriptor("Variable", FishHighlighterColors.VARIABLE),
                AttributesDescriptor("Number", FishHighlighterColors.NUMBER),
                AttributesDescriptor("Command", FishHighlighterColors.COMMAND),
                AttributesDescriptor("Operator", FishHighlighterColors.OPERATOR),
                AttributesDescriptor("Redirection", FishHighlighterColors.REDIRECT),
                AttributesDescriptor("Parentheses", FishHighlighterColors.PARENTHESES),
                AttributesDescriptor("Braces", FishHighlighterColors.BRACES),
                AttributesDescriptor("Brackets", FishHighlighterColors.BRACKETS),
                AttributesDescriptor("Bad character", FishHighlighterColors.BAD_CHARACTER),
            )

        private val DEMO_TEXT = """#!/usr/bin/env fish

# Keywords: if, for, function, while, switch, case, begin, end, break, continue
function greet --description 'Demonstrate Fish syntax'
    # Variables: regular, special ($1, $@, $*), and command substitution
    set name ${'$'}argv[1]
    set count 42
    set result (math 1 + 2)

    # Strings with escape sequences: \n \t \xHH \uHHHH
    echo "Hello, ${'$'}name!\nTab:\tHex:\x41"
    echo 'Single quoted: no ${'$'}expansion here'

    # Numbers and operators
    if test ${'$'}count -gt 10
        echo "Count is ${'$'}count"
    end

    # Subcommand keywords: builtin, command, exec, time
    builtin echo "Using builtin"
    command ls -la
    time sleep 0.1

    # Loops with brackets and braces
    for i in {1..5}
        echo "Item ${'$'}i: ${'$'}argv[${'$'}i]"
    end

    # Switch/case
    switch ${'$'}name
        case alice bob
            echo "Known user"
        case '*'
            echo "Unknown"
    end
end

# Commands with pipes, redirections, and logical operators
cat file.txt | grep "pattern" > output.txt 2>&1
ls -la && echo "Success" || echo "Failed"

# Background process and parentheses for command substitution
long_task &
set files (ls *.fish)

# While loop and semicolons
set x 0; while test ${'$'}x -lt 3
    echo ${'$'}x
    set x (math ${'$'}x + 1)
end

# Bad characters would be highlighted as errors if present
"""
    }
}
