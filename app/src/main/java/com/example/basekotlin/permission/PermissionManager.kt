package com.example.basekotlin.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

object PermissionManager {
    fun videoPermission(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_VIDEO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    }

    fun hasVideoPermission(context: Context): Boolean =
        ContextCompat.checkSelfPermission(
            context,
            videoPermission(),
        ) == PackageManager.PERMISSION_GRANTED
}
