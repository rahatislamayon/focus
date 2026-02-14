package com.study.focus.service

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.content.Intent
import com.study.focus.data.FocusTimerManager

class AppBlockingService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        // Configure service here if needed dynamically
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (!FocusTimerManager.isSessionActive.value) return

        event?.let {
            if (it.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                val packageName = it.packageName?.toString() ?: return
                if (isAppBlocked(packageName)) {
                    // Trigger overlay or go home
                    performGlobalAction(GLOBAL_ACTION_HOME)
                    OverlayManager.showOverlay(this)
                }
            }
        }
    }

    private fun isAppBlocked(packageName: String): Boolean {
        // TODO: Load from repository/preferences
        val blockedApps = listOf(
            "com.instagram.android",
            "com.facebook.katana",
            "com.google.android.youtube",
            "com.zhiliaoapp.musically", // TikTok
            "com.twitter.android",
            "com.reddit.frontpage"
        )
        return blockedApps.contains(packageName)
    }

    override fun onInterrupt() {
        // Handle interruption
    }
}
