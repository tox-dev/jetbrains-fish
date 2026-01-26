package com.github.toxdev.fish.lsp

import com.intellij.ide.BrowserUtil
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.Project

object FishLspNotification {
    const val NOTIFICATION_GROUP_ID = "Fish LSP"

    fun notifyFishLspNotFound(project: Project) {
        val settings = FishLspSettings.getInstance()
        if (settings.notificationDismissed) return

        NotificationGroupManager
            .getInstance()
            .getNotificationGroup(NOTIFICATION_GROUP_ID)
            .createNotification(
                "fish-lsp not found",
                "Install fish-lsp for code intelligence features like completion, " +
                    "go-to-definition, and diagnostics.",
                NotificationType.WARNING,
            ).addAction(
                NotificationAction.createSimpleExpiring("Installation Instructions") {
                    BrowserUtil.browse("https://github.com/ndonfris/fish-lsp#installation")
                },
            ).addAction(
                NotificationAction.createSimpleExpiring("Open Settings") {
                    ShowSettingsUtil.getInstance().showSettingsDialog(project, FishLspConfigurable::class.java)
                },
            ).addAction(
                NotificationAction.createSimpleExpiring("Don't Show Again") {
                    settings.notificationDismissed = true
                },
            ).notify(project)
    }
}
