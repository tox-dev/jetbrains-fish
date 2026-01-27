package com.github.toxdev.fish

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import com.intellij.ui.ColorUtil
import com.intellij.ui.JBColor
import com.intellij.util.ui.ColorIcon
import java.awt.Color
import javax.swing.Icon

class FishColorPreviewProvider : LineMarkerProvider {
    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? {
        if (element.node.elementType.toString() != "WORD") return null

        val text = element.text
        if (text != "set_color") return null

        val nextSibling = findColorArgument(element) ?: return null
        val color = parseColor(nextSibling) ?: return null

        return LineMarkerInfo(
            element,
            element.textRange,
            createColorIcon(color),
            { "Color: $nextSibling" },
            null,
            GutterIconRenderer.Alignment.LEFT,
            { "Color preview" },
        )
    }

    private fun findColorArgument(setColorElement: PsiElement): String? {
        var sibling = setColorElement.nextSibling
        while (sibling != null) {
            val type = sibling.node.elementType.toString()
            if (type == "WORD" || type == "BARE_WORD") {
                val text = sibling.text
                if (!text.startsWith("-")) {
                    return text
                }
            }
            sibling = sibling.nextSibling
        }
        return null
    }

    private fun parseColor(colorName: String): Color? = FISH_COLORS[colorName.lowercase()] ?: parseHexColor(colorName)

    private fun parseHexColor(hex: String): Color? {
        val cleaned = hex.removePrefix("#")
        return try {
            when (cleaned.length) {
                3 -> {
                    val r = cleaned[0].toString().repeat(2).toInt(16)
                    val g = cleaned[1].toString().repeat(2).toInt(16)
                    val b = cleaned[2].toString().repeat(2).toInt(16)
                    Color(r, g, b)
                }
                6 -> ColorUtil.fromHex(cleaned)
                else -> null
            }
        } catch (_: NumberFormatException) {
            null
        }
    }

    private fun createColorIcon(color: Color): Icon = ColorIcon(12, color, true)

    companion object {
        private val FISH_COLORS =
            mapOf(
                "black" to JBColor.BLACK,
                "red" to Color(205, 0, 0),
                "green" to Color(0, 205, 0),
                "yellow" to Color(205, 205, 0),
                "blue" to Color(0, 0, 238),
                "magenta" to Color(205, 0, 205),
                "cyan" to Color(0, 205, 205),
                "white" to Color(229, 229, 229),
                "brblack" to Color(127, 127, 127),
                "brred" to Color(255, 0, 0),
                "brgreen" to Color(0, 255, 0),
                "bryellow" to Color(255, 255, 0),
                "brblue" to Color(92, 92, 255),
                "brmagenta" to Color(255, 0, 255),
                "brcyan" to Color(0, 255, 255),
                "brwhite" to JBColor.WHITE,
                "normal" to Color(192, 192, 192),
            )
    }
}
