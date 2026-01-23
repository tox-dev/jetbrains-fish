package com.github.toxdev.fish

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

/**
 * Icons used by the Fish shell plugin.
 */
object FishIcons {
    @JvmField
    val FILE: Icon = IconLoader.getIcon("/icons/fish.svg", FishIcons::class.java)
}
