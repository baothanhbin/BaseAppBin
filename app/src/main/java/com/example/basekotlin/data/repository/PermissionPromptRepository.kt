package com.example.basekotlin.data.repository

import android.content.Context
import com.example.basekotlin.util.SharedPreUtils

class PermissionPromptRepository(
    private val context: Context,
) {

    fun notificationPermissionDenyCount(): Int {
        return SharedPreUtils.getInstance().getNotificationPermissionDenyCount(context)
    }

    fun markNotificationPermissionDenied(): Int {
        return SharedPreUtils.getInstance().setNotificationPermissionDenyCount(context)
    }

    fun resetNotificationPermissionDenials() {
        SharedPreUtils.getInstance().resetNotificationPermissionDenyCount(context)
    }

    fun videoPermissionDenyCount(): Int {
        return notificationPermissionDenyCount()
    }

    fun markVideoPermissionDenied(): Int {
        return markNotificationPermissionDenied()
    }

    fun resetVideoPermissionDenials() {
        resetNotificationPermissionDenials()
    }
}
