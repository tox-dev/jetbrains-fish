package com.github.toxdev.fish.features

import com.github.toxdev.fish.FishColorPreviewProvider
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.awt.Color

class FishColorPreviewProviderTest {
    @Test
    fun `parseColor returns color for named color black`() {
        val color = FishColorPreviewProvider.parseColor("black")
        assertNotNull(color)
    }

    @Test
    fun `parseColor returns color for named color red`() {
        val color = FishColorPreviewProvider.parseColor("red")
        assertNotNull(color)
        assertEquals(205, color!!.red)
        assertEquals(0, color.green)
        assertEquals(0, color.blue)
    }

    @Test
    fun `parseColor returns color for named color green`() {
        val color = FishColorPreviewProvider.parseColor("green")
        assertNotNull(color)
        assertEquals(0, color!!.red)
        assertEquals(205, color.green)
        assertEquals(0, color.blue)
    }

    @Test
    fun `parseColor returns color for named color yellow`() {
        assertNotNull(FishColorPreviewProvider.parseColor("yellow"))
    }

    @Test
    fun `parseColor returns color for named color blue`() {
        assertNotNull(FishColorPreviewProvider.parseColor("blue"))
    }

    @Test
    fun `parseColor returns color for named color magenta`() {
        assertNotNull(FishColorPreviewProvider.parseColor("magenta"))
    }

    @Test
    fun `parseColor returns color for named color cyan`() {
        assertNotNull(FishColorPreviewProvider.parseColor("cyan"))
    }

    @Test
    fun `parseColor returns color for named color white`() {
        assertNotNull(FishColorPreviewProvider.parseColor("white"))
    }

    @Test
    fun `parseColor returns color for bright colors`() {
        assertNotNull(FishColorPreviewProvider.parseColor("brblack"))
        assertNotNull(FishColorPreviewProvider.parseColor("brred"))
        assertNotNull(FishColorPreviewProvider.parseColor("brgreen"))
        assertNotNull(FishColorPreviewProvider.parseColor("bryellow"))
        assertNotNull(FishColorPreviewProvider.parseColor("brblue"))
        assertNotNull(FishColorPreviewProvider.parseColor("brmagenta"))
        assertNotNull(FishColorPreviewProvider.parseColor("brcyan"))
        assertNotNull(FishColorPreviewProvider.parseColor("brwhite"))
    }

    @Test
    fun `parseColor returns color for normal`() {
        assertNotNull(FishColorPreviewProvider.parseColor("normal"))
    }

    @Test
    fun `parseColor is case insensitive`() {
        assertNotNull(FishColorPreviewProvider.parseColor("RED"))
        assertNotNull(FishColorPreviewProvider.parseColor("Red"))
        assertNotNull(FishColorPreviewProvider.parseColor("GREEN"))
    }

    @Test
    fun `parseColor returns null for unknown color name`() {
        assertNull(FishColorPreviewProvider.parseColor("notacolor"))
        assertNull(FishColorPreviewProvider.parseColor("purple"))
        assertNull(FishColorPreviewProvider.parseColor("orange"))
    }

    @Test
    fun `parseHexColor parses 6-digit hex`() {
        val color = FishColorPreviewProvider.parseHexColor("ff0000")
        assertNotNull(color)
        assertEquals(255, color!!.red)
        assertEquals(0, color.green)
        assertEquals(0, color.blue)
    }

    @Test
    fun `parseHexColor parses 6-digit hex with hash prefix`() {
        val color = FishColorPreviewProvider.parseHexColor("#00ff00")
        assertNotNull(color)
        assertEquals(0, color!!.red)
        assertEquals(255, color.green)
        assertEquals(0, color.blue)
    }

    @Test
    fun `parseHexColor parses 3-digit hex`() {
        val color = FishColorPreviewProvider.parseHexColor("f00")
        assertNotNull(color)
        assertEquals(255, color!!.red)
        assertEquals(0, color.green)
        assertEquals(0, color.blue)
    }

    @Test
    fun `parseHexColor parses 3-digit hex with hash prefix`() {
        val color = FishColorPreviewProvider.parseHexColor("#0f0")
        assertNotNull(color)
        assertEquals(0, color!!.red)
        assertEquals(255, color.green)
        assertEquals(0, color.blue)
    }

    @Test
    fun `parseHexColor returns null for invalid length`() {
        assertNull(FishColorPreviewProvider.parseHexColor("ff00"))
        assertNull(FishColorPreviewProvider.parseHexColor("ff00000"))
        assertNull(FishColorPreviewProvider.parseHexColor("f"))
        assertNull(FishColorPreviewProvider.parseHexColor(""))
    }

    @Test
    fun `parseHexColor returns null for invalid hex characters`() {
        assertNull(FishColorPreviewProvider.parseHexColor("gggggg"))
        assertNull(FishColorPreviewProvider.parseHexColor("zzzzzz"))
    }

    @Test
    fun `parseColor falls back to hex parsing`() {
        val color = FishColorPreviewProvider.parseColor("0000ff")
        assertNotNull(color)
        assertEquals(0, color!!.red)
        assertEquals(0, color.green)
        assertEquals(255, color.blue)
    }

    @Test
    fun `createColorIcon creates icon with correct size`() {
        val icon = FishColorPreviewProvider.createColorIcon(Color.RED)
        assertNotNull(icon)
        assertEquals(12, icon.iconWidth)
        assertEquals(12, icon.iconHeight)
    }

    @Test
    fun `FISH_COLORS contains all expected colors`() {
        assertEquals(17, FishColorPreviewProvider.FISH_COLORS.size)
    }

    @Test
    fun `provider is instantiable`() {
        val provider = FishColorPreviewProvider()
        assertNotNull(provider)
    }
}

@com.intellij.testFramework.junit5.TestApplication
class FishColorPreviewProviderPlatformTest {
    private val project get() =
        com.intellij.openapi.project.ProjectManager
            .getInstance()
            .defaultProject

    private fun createPsiFile(content: String): com.github.toxdev.fish.psi.FishFile {
        val factory =
            com.intellij.psi.PsiFileFactory
                .getInstance(project)
        return factory.createFileFromText(
            "test.fish",
            com.github.toxdev.fish.FishFileType.INSTANCE,
            content,
        ) as com.github.toxdev.fish.psi.FishFile
    }

    @Test
    fun `getLineMarkerInfo returns null for non-WORD elements`() {
        val provider = FishColorPreviewProvider()
        val content = "# comment"
        val file =
            com.intellij.openapi.application.ApplicationManager.getApplication().runReadAction<com.github.toxdev.fish.psi.FishFile> {
                createPsiFile(content)
            }

        com.intellij.openapi.application.ApplicationManager.getApplication().runReadAction {
            val element = file.firstChild
            if (element != null) {
                val info = provider.getLineMarkerInfo(element)
                assertNull(info)
            }
        }
    }

    @Test
    fun `getLineMarkerInfo returns null for non set_color WORD`() {
        val provider = FishColorPreviewProvider()
        val content = "echo hello"
        val file =
            com.intellij.openapi.application.ApplicationManager.getApplication().runReadAction<com.github.toxdev.fish.psi.FishFile> {
                createPsiFile(content)
            }

        com.intellij.openapi.application.ApplicationManager.getApplication().runReadAction {
            file.node.getChildren(null).forEach { node ->
                if (node.elementType.toString() == "WORD" && node.text != "set_color") {
                    val info = provider.getLineMarkerInfo(node.psi)
                    assertNull(info)
                }
            }
        }
    }

    @Test
    fun `getLineMarkerInfo returns null for set_color with invalid color`() {
        val provider = FishColorPreviewProvider()
        val content = "set_color notacolor"
        val file =
            com.intellij.openapi.application.ApplicationManager.getApplication().runReadAction<com.github.toxdev.fish.psi.FishFile> {
                createPsiFile(content)
            }

        com.intellij.openapi.application.ApplicationManager.getApplication().runReadAction {
            com.intellij.psi.util.PsiTreeUtil.processElements(file) { element ->
                if (element.text == "set_color") {
                    val info = provider.getLineMarkerInfo(element)
                    assertNull(info)
                }
                true
            }
        }
    }

    @Test
    fun `getLineMarkerInfo returns null when no color argument follows set_color`() {
        val provider = FishColorPreviewProvider()
        val content = "set_color"
        val file =
            com.intellij.openapi.application.ApplicationManager.getApplication().runReadAction<com.github.toxdev.fish.psi.FishFile> {
                createPsiFile(content)
            }

        com.intellij.openapi.application.ApplicationManager.getApplication().runReadAction {
            com.intellij.psi.util.PsiTreeUtil.processElements(file) { element ->
                if (element.text == "set_color") {
                    val info = provider.getLineMarkerInfo(element)
                    assertNull(info)
                }
                true
            }
        }
    }
}
